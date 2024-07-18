/**
 * 
 */
package sauce.agua.rest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.ClienteNotFoundException;
import sauce.agua.rest.model.Cliente;
import sauce.agua.rest.model.ICliente;
import sauce.agua.rest.model.view.ActivoConMedidor;
import sauce.agua.rest.model.view.ClienteRecorrido;
import sauce.agua.rest.model.view.ClienteSearch;
import sauce.agua.rest.model.view.DeudorPlanCorte;
import sauce.agua.rest.model.view.SocioActivo;
import sauce.agua.rest.model.view.SocioActivoConCuotaFija;
import sauce.agua.rest.model.view.SocioActivoConMedidor;
import sauce.agua.rest.repository.IClienteRepository;
import sauce.agua.rest.repository.view.IActivoConMedidorRepository;
import sauce.agua.rest.repository.view.IClienteRecorridoRepository;
import sauce.agua.rest.repository.view.IClienteSearchRepository;
import sauce.agua.rest.repository.view.IDeudorFactura2Repository;
import sauce.agua.rest.repository.view.IDeudorFactura60DiasRepository;
import sauce.agua.rest.repository.view.IDeudorPlanCorteRepository;
import sauce.agua.rest.repository.view.ISocioActivoConCuotaFijaRepository;
import sauce.agua.rest.repository.view.ISocioActivoConMedidorRepository;
import sauce.agua.rest.repository.view.ISocioActivoRepository;
import sauce.agua.rest.service.view.ClienteRecorridoNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class ClienteService {

	@Autowired
	private IClienteRepository repository;

	@Autowired
	private IClienteSearchRepository clientesearchrepository;

	@Autowired
	private IClienteRecorridoRepository clienterecorridorepository;

	@Autowired
	private IActivoConMedidorRepository activoconmedidorrepository;

	@Autowired
	private ISocioActivoRepository socioactivorepository;

	@Autowired
	private ISocioActivoConMedidorRepository socioactivoconmedidorrepository;

	@Autowired
	private ISocioActivoConCuotaFijaRepository socioactivoconcuotafijarepository;

	@Autowired
	private IDeudorPlanCorteRepository deudorplancorterepository;

	@Autowired
	private IDeudorFactura60DiasRepository deudorfactura60diasrepository;

	@Autowired
	private IDeudorFactura2Repository deudorfactura2repository;

	public List<Cliente> findAllByClienteId(Long clienteId) {
		return repository.findAllByClienteId(clienteId, Sort.by("fechaalta").ascending());
	}

	public List<Cliente> findAllActivos(Boolean byname) {
		Sort sort = Sort.by("clienteId").ascending();
		if (byname)
			sort = Sort.by("apellido").ascending().and(Sort.by("nombre").ascending())
					.and(Sort.by("clienteId").ascending());
		return repository.findAllByFechaBajaIsNull(sort);
	}

	public List<Cliente> findAllActivos2Lectura(Integer zona, Integer ruta) {
		return repository.findAllByFechaBajaIsNullAndZonaAndRuta(zona, ruta, Sort.by("orden").ascending());
	}

	public List<Cliente> findAllActivosByZona() {
		return repository.findAllByFechaBajaIsNull(Sort.by("zona").ascending());
	}

	public List<Cliente> findAllActivosByRuta(Integer zona) {
		return repository.findAllByFechaBajaIsNullAndZona(zona, Sort.by("ruta").ascending());
	}

	public List<Cliente> findAllActivosByZonaRuta(Integer zona, Integer ruta) {
		return repository.findAllByFechaBajaIsNullAndZonaAndRuta(zona, ruta,
				Sort.by("orden").ascending().and(Sort.by("clienteId").ascending()));
	}

	public List<Cliente> findAllActivosByZonaRutaOtros(Integer zona, Integer ruta) {
		Map<Long, Cliente> todos = repository.findAllByFechaBajaIsNull(Sort.by("clienteId").ascending()).stream()
				.collect(Collectors.toMap(Cliente::getClienteId, cliente -> cliente));
		List<Long> ids = new ArrayList<Long>();
		for (Cliente cliente : repository.findAllByFechaBajaIsNullAndZonaAndRuta(zona, ruta,
				Sort.by("orden").ascending()))
			ids.add(cliente.getClienteId());
		todos.keySet().removeAll(new HashSet<>(ids));
		return new ArrayList<Cliente>(todos.values());
	}

	public List<Cliente> findAllActivosMedibles() {
		return repository.findAllByFechaBajaIsNullAndCobroLessThan(3, Sort.by("clienteId").ascending());
	}

	public List<Cliente> findAllActivosZona(Integer zona) {
		return repository.findAllByFechaBajaIsNullAndZona(zona,
				Sort.by("zona").ascending().and(Sort.by("ruta").ascending()).and(Sort.by("orden").ascending())
						.and(Sort.by("clienteId").ascending()));
	}

	public List<Cliente> findAllActivosRango(Long clienteIddesde, Long clienteIdhasta) {
		return repository.findAllByFechaBajaIsNullAndClienteIdBetween(clienteIddesde, clienteIdhasta,
				Sort.by("clienteId").ascending());
	}

	public List<Cliente> findAllActivosConCuotaFija() {
		return repository.findAllByFechaBajaIsNullAndCobro(3, Sort.by("apellido").ascending()
				.and(Sort.by("nombre").ascending()).and(Sort.by("clienteId").ascending()));
	}

	public List<ClienteSearch> findAllSearch(String search) {
		log.debug("Chain " + search);
		return clientesearchrepository.findTop50BySearchLike("%" + search + "%", Sort.by("search").ascending());
	}

	public List<ActivoConMedidor> findAllActivosConMedidor() {
		return activoconmedidorrepository.findAll();
	}

	public List<SocioActivo> findAllSociosActivos() {
		return socioactivorepository.findAll();
	}

	public List<SocioActivoConMedidor> findAllSociosActivosConMedidor() {
		return socioactivoconmedidorrepository.findAll();
	}

	public List<SocioActivoConCuotaFija> findAllSociosActivosConCuotaFija() {
		return socioactivoconcuotafijarepository.findAll();
	}

	public List<DeudorPlanCorte> findAllDeudoresPlanCorte() {
		return deudorplancorterepository.findAll(Sort.by("clienteId").ascending());
	}

	public List<ICliente> findAllDeudoresFactura60Dias() {
		Set<ICliente> deudores = new HashSet<ICliente>(
				deudorfactura60diasrepository.findAll(Sort.by("clienteId").ascending()));
		deudores.addAll(deudorfactura2repository.findAll(Sort.by("clienteId").ascending()));
		return new ArrayList<ICliente>(deudores);
	}

	public Cliente findByUniqueId(Long uniqueId) {
		return repository.findByUniqueId(uniqueId).orElseThrow(() -> new ClienteNotFoundException(uniqueId));
	}

	public Cliente findLastByClienteId(Long clienteId) {
		return repository.findTopByClienteIdOrderByFechaAltaDesc(clienteId)
				.orElseThrow(() -> new ClienteNotFoundException(clienteId));
	}

	public Cliente findLastCliente() {
		return repository.findTopByOrderByClienteIdDescFechaAltaDesc()
				.orElseThrow(() -> new ClienteNotFoundException());
	}

	public Cliente add(Cliente cliente) {
		repository.save(cliente);
		log.debug("Cliente -> " + cliente.toString());
		return cliente;
	}

	public Cliente update(Cliente newCliente, Long uniqueId) {
		return repository.findByUniqueId(uniqueId).map(cliente -> {
			try {
				log.debug("Cliente 1 -> " + JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(newCliente));
			} catch (JsonProcessingException e) {
				log.debug("Sin cliente");
			}
			cliente = new Cliente(uniqueId, newCliente.getClienteId(), newCliente.getFechaAlta(),
					newCliente.getFechaBaja(), newCliente.getApellido(), newCliente.getNombre(),
					newCliente.getNumeroSocio(), newCliente.getInmuebleCalle(), newCliente.getInmueblePuerta(),
					newCliente.getInmueblePiso(), newCliente.getInmuebleDpto(), newCliente.getInmuebleLocalidad(),
					newCliente.getInmuebleProvincia(), newCliente.getInmuebleCodpostal(), newCliente.getFiscalCalle(),
					newCliente.getFiscalPuerta(), newCliente.getFiscalPiso(), newCliente.getFiscalDpto(),
					newCliente.getFiscalLocalidad(), newCliente.getFiscalProvincia(), newCliente.getFiscalCodpostal(),
					newCliente.getCuit(), newCliente.getSituacionIva(), newCliente.getNombreCategoria(),
					newCliente.getCategoria(), newCliente.getServicio(), newCliente.getCobro(), newCliente.getZona(),
					newCliente.getRuta(), newCliente.getOrden(), newCliente.getCortado(), newCliente.getEstadoId(),
					newCliente.getFechaNacimiento(), newCliente.getCategoriasocioId(), newCliente.getDestinoId(),
					newCliente.getUid());
			try {
				log.debug("Cliente 2 -> " + JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(newCliente));
			} catch (JsonProcessingException e) {
				log.debug("Sin cliente");
			}
			cliente = repository.save(cliente);
			try {
				log.debug("Cliente 3 -> " + JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(newCliente));
			} catch (JsonProcessingException e) {
				log.debug("Sin cliente");
			}
			return cliente;
		}).orElseThrow(() -> new ClienteNotFoundException(uniqueId));
	}

	public ClienteRecorrido findNextCliente(Long clienteId) {
		ClienteRecorrido cliente = clienterecorridorepository.findByClienteId(clienteId)
				.orElseThrow(() -> new ClienteRecorridoNotFoundException(clienteId));
		log.debug("Cliente -> " + cliente);
		Long recorrido = ((cliente.getZona() * 1000L) + cliente.getRuta()) * 10000L + cliente.getOrden();
		return clienterecorridorepository.findFirstByRecorridoGreaterThan(recorrido)
				.orElseThrow(() -> new ClienteRecorridoNotFoundException(clienteId));
	}

}
