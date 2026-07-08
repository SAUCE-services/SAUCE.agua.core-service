/**
 * 
 */
package sauce.agua.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import sauce.agua.rest.exception.ClienteException;
import sauce.agua.rest.model.Cliente;
import sauce.agua.rest.model.ICliente;
import sauce.agua.rest.model.view.ActivoConMedidor;
import sauce.agua.rest.model.view.ClienteRecorrido;
import sauce.agua.rest.model.view.ClienteSearch;
import sauce.agua.rest.model.view.DeudorPlanCorte;
import sauce.agua.rest.model.view.SocioActivo;
import sauce.agua.rest.model.view.SocioActivoConCuotaFija;
import sauce.agua.rest.model.view.SocioActivoConMedidor;
import sauce.agua.rest.service.ClienteService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/cliente", "/api/core/cliente"})
public class ClienteController {

	private final ClienteService service;

	public ClienteController(ClienteService service) {
		this.service = service;
	}

	@PostMapping("/search")
	public ResponseEntity<List<ClienteSearch>> findAllSearch(@RequestBody List<String> chain) {
		return ResponseEntity.ok(service.findAllSearch(chain.get(0)));
	}

	@GetMapping("/bycliente/{clienteId}")
	public ResponseEntity<List<Cliente>> findAllByClienteId(@PathVariable Long clienteId) {
		return ResponseEntity.ok(service.findAllByClienteId(clienteId));
	}

	@GetMapping("/activos/{byname}")
	public ResponseEntity<List<Cliente>> findAllActivos(@PathVariable Boolean byname) {
		return ResponseEntity.ok(service.findAllActivos(byname));
	}

	@GetMapping("/activos2lectura/{zona}/{ruta}")
	public ResponseEntity<List<Cliente>> findAllActivos2Lectura(@PathVariable Integer zona,
			@PathVariable Integer ruta) {
		return ResponseEntity.ok(service.findAllActivos2Lectura(zona, ruta));
	}

	@GetMapping("/activosbyzona")
	public ResponseEntity<List<Cliente>> findAllActivosByZona() {
		return ResponseEntity.ok(service.findAllActivosByZona());
	}

	@GetMapping("/activosbyruta/{zona}")
	public ResponseEntity<List<Cliente>> findAllActivosByRuta(@PathVariable Integer zona) {
		return ResponseEntity.ok(service.findAllActivosByRuta(zona));
	}

	@GetMapping("/activosmedibles")
	public ResponseEntity<List<Cliente>> findAllActivosMedibles() {
		return ResponseEntity.ok(service.findAllActivosMedibles());
	}

	@GetMapping("/activosbyzonaruta/{zona}/{ruta}")
	public ResponseEntity<List<Cliente>> findAllActivosByZonaRuta(@PathVariable Integer zona,
			@PathVariable Integer ruta) {
		return ResponseEntity.ok(service.findAllActivosByZonaRuta(zona, ruta));
	}

	@GetMapping("/activosbyzonarutaotros/{zona}/{ruta}")
	public ResponseEntity<List<Cliente>> findAllActivosByZonaRutaOtros(@PathVariable Integer zona,
			@PathVariable Integer ruta) {
		return ResponseEntity.ok(service.findAllActivosByZonaRutaOtros(zona, ruta));
	}

	@GetMapping("/activosconcuotafija")
	public ResponseEntity<List<Cliente>> findAllActivosConCuotaFija() {
		return ResponseEntity.ok(service.findAllActivosConCuotaFija());
	}

	@GetMapping("/activosconmedidor")
	public ResponseEntity<List<ActivoConMedidor>> findAllActivosConMedidor() {
		return ResponseEntity.ok(service.findAllActivosConMedidor());
	}

	@GetMapping("/zona/{zona}")
	public ResponseEntity<List<Cliente>> findAllActivosZona(@PathVariable Integer zona) {
		return ResponseEntity.ok(service.findAllActivosZona(zona));
	}

	@GetMapping("/rango/{clienteIddesde}/{clienteIdhasta}")
	public ResponseEntity<List<Cliente>> findAllActivosRango(@PathVariable Long clienteIddesde,
			@PathVariable Long clienteIdhasta) {
		return ResponseEntity.ok(service.findAllActivosRango(clienteIddesde, clienteIdhasta));
	}

	@GetMapping("/sociosactivosconmedidor")
	public ResponseEntity<List<SocioActivoConMedidor>> findAllSociosActivosConMedidor() {
		return ResponseEntity.ok(service.findAllSociosActivosConMedidor());
	}

	@GetMapping("/sociosactivos")
	public ResponseEntity<List<SocioActivo>> findAllSociosActivos() {
		return ResponseEntity.ok(service.findAllSociosActivos());
	}

	@GetMapping("/sociosactivosconcuotafija")
	public ResponseEntity<List<SocioActivoConCuotaFija>> findAllSociosActivosConCuotaFija() {
		return ResponseEntity.ok(service.findAllSociosActivosConCuotaFija());
	}

	@GetMapping("/deudoresplancorte")
	public ResponseEntity<List<DeudorPlanCorte>> findAllDeudoresPlanCorte() {
		return ResponseEntity.ok(service.findAllDeudoresPlanCorte());
	}

	@GetMapping("/deudoresfactura60dias")
	public ResponseEntity<List<ICliente>> findAllDeudoresFactura60Dias() {
		return ResponseEntity.ok(service.findAllDeudoresFactura60Dias());
	}
	
	@GetMapping("/{uniqueId}")
	public ResponseEntity<Cliente> findByUniqueId(@PathVariable Long uniqueId) {
		try {
			return ResponseEntity.ok(service.findByUniqueId(uniqueId));
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/lastbyclienteId/{clienteId}")
	public ResponseEntity<Cliente> findLastByClienteId(@PathVariable Long clienteId) {
		try {
			return ResponseEntity.ok(service.findLastByClienteId(clienteId));
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/lastcliente")
	public ResponseEntity<Cliente> findLastCliente() {
		try {
			return ResponseEntity.ok(service.findLastCliente());
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/nextbyclienteId/{clienteId}")
	public ResponseEntity<ClienteRecorrido> findNextCliente(@PathVariable Long clienteId) {
		try {
			return ResponseEntity.ok(service.findNextCliente(clienteId));
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Cliente> add(@RequestBody Cliente cliente) {
		return ResponseEntity.ok(service.add(cliente));
	}
	
	@PutMapping("/{uniqueId}")
	public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable Long uniqueId) {
		return ResponseEntity.ok(service.update(cliente, uniqueId));
	}

}
