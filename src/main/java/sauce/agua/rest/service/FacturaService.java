/**
 * 
 */
package sauce.agua.rest.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.FacturaException;
import sauce.agua.rest.model.Cliente;
import sauce.agua.rest.model.Factura;
import sauce.agua.rest.repository.FacturaRepository;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class FacturaService {

	private final FacturaRepository repository;
	private final ClienteService clienteService;

	public FacturaService(FacturaRepository repository,
						  ClienteService clienteService) {
		this.repository = repository;
		this.clienteService = clienteService;
	}

	public List<Factura> findAllByPeriodoId(Integer periodoId) {
		return repository.findAllByPeriodoIdAndAnulada(periodoId, (byte) 0);
	}

	public List<Factura> findAllByPeriodoIdAndZona(Integer periodoId, Integer zona) {
		log.debug("Processing findAllByPeriodoIdAndZona: periodoId={}, zona={}", periodoId, zona);
		
		// Obtener clientes activos de la zona y convertir a Map una sola vez
		Map<Long, Cliente> clientesZona = clienteService.findAllActivosZona(zona)
				.stream()
				.filter(cliente -> Objects.equals(cliente.getZona(), zona))
				.peek(this::logCliente)
				.collect(Collectors.toMap(Cliente::getClienteId, cliente -> cliente));

		// Filtrar facturas usando el Map de clientes
		return repository.findAllByPeriodoIdAndAnulada(periodoId, (byte) 0)
				.stream()
				.filter(factura -> clientesZona.containsKey(factura.getClienteId()))
				.peek(this::logFactura)
				.collect(Collectors.toList());
	}

	public List<Factura> findAllDeudaByPeriodoIdAndClienteIds(Integer periodoId, List<Long> clienteIds) {
		return repository.findAllByPeriodoIdAndPagadaAndAnuladaAndCanceladaAndClienteIdIn(periodoId, (byte) 0, (byte) 0,
				(byte) 0, clienteIds);
	}

	public List<Factura> findAllByUniqueIdIn(List<Long> uniqueIds) {
		return repository.findAllByUniqueIdIn(uniqueIds);
	}

	public List<Factura> findAllByDeudaPrint(Long clienteId, Integer periodoIdReferencia) {
		log.debug("Processing core - FacturaService.findAllByDeudaPrint");
		var facturas = repository.findTop6ByClienteIdAndPeriodoIdLessThanAndPagadaAndAnuladaAndCanceladaOrderByFacturaIdDesc(
				clienteId, periodoIdReferencia, (byte) 0, (byte) 0, (byte) 0);
		logFacturas(facturas);
		return facturas;
	}

	public Factura findByFactura(Integer prefijoId, Long facturaId) {
		log.debug("Processing core - FacturaService.findByFactura");
		var factura = repository.findByPrefijoIdAndFacturaId(prefijoId, facturaId)
				.orElseThrow(() -> new FacturaException(prefijoId, facturaId));
		logFactura(factura);
		return factura;
	}

	private void logCliente(Cliente cliente) {
		try {
			log.debug("Cliente: {}", JsonMapper
					.builder()
					.findAndAddModules()
					.build()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(cliente));
		} catch (JsonProcessingException e) {
			log.debug("Cliente jsonify error: {}", e.getMessage());
		}
	}

	private void logFactura(Factura factura) {
		try {
			log.debug("Factura: {}", JsonMapper
					.builder()
					.findAndAddModules()
					.build()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(factura));
		} catch (JsonProcessingException e) {
			log.debug("Factura jsonify error: {}", e.getMessage());
		}
	}

	private void logFacturas(List<Factura> facturas) {
		try {
			log.debug("Facturas: {}", JsonMapper
					.builder()
					.findAndAddModules()
					.build()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(facturas));
		} catch (JsonProcessingException e) {
			log.debug("Facturas jsonify error: {}", e.getMessage());
		}
	}

}
