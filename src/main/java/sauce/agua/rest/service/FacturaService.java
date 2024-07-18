/**
 * 
 */
package sauce.agua.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.FacturaNotFoundException;
import sauce.agua.rest.model.Cliente;
import sauce.agua.rest.model.Factura;
import sauce.agua.rest.repository.IClienteRepository;
import sauce.agua.rest.repository.IFacturaRepository;

/**
 * @author daniel
 *
 */
@Service
public class FacturaService {
	@Autowired
	private IFacturaRepository repository;

	@Autowired
	private IClienteRepository clienterepository;

	public List<Factura> findAllByPeriodoId(Integer periodoId) {
		return repository.findAllByPeriodoIdAndAnulada(periodoId, (byte) 0);
	}

	public List<Factura> findAllByPeriodoIdAndZona(Integer periodoId, Integer zona) {
		List<Factura> facturas = new ArrayList<Factura>();
		Map<Long, Cliente> clientes = clienterepository.findAllByFechaBajaIsNull(Sort.by("clienteId").ascending())
				.stream().collect(Collectors.toMap(Cliente::getClienteId, cliente -> cliente));
		for (Factura factura : repository.findAllByPeriodoIdAndAnulada(periodoId, (byte) 0)) {
			if (clientes.get(factura.getClienteId()).getZona() == zona)
				facturas.add(factura);
		}
		return facturas;
	}

	public List<Factura> findAllDeudaByPeriodoIdAndClienteIds(Integer periodoId, List<Long> clienteIds) {
		return repository.findAllByPeriodoIdAndPagadaAndAnuladaAndCanceladaAndClienteIdIn(periodoId, (byte) 0, (byte) 0,
				(byte) 0, clienteIds);
	}

	public List<Factura> findAllByUniqueIdIn(List<Long> uniqueIds) {
		return repository.findAllByUniqueIdIn(uniqueIds);
	}

	public List<Factura> findAllByDeudaPrint(Long clienteId, Integer periodoIdReferencia) {
		return repository.findTop6ByClienteIdAndPeriodoIdLessThanAndPagadaAndAnuladaAndCanceladaOrderByFacturaIdDesc(
				clienteId, periodoIdReferencia, (byte) 0, (byte) 0, (byte) 0);
	}

	public Factura findByFactura(Integer prefijoId, Long facturaId) {
		return repository.findByPrefijoIdAndFacturaId(prefijoId, facturaId)
				.orElseThrow(() -> new FacturaNotFoundException(prefijoId, facturaId));
	}

}
