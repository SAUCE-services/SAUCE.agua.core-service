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
		return new ResponseEntity<>(service.findAllSearch(chain.get(0)), HttpStatus.OK);
	}

	@GetMapping("/bycliente/{clienteId}")
	public ResponseEntity<List<Cliente>> findAllByClienteId(@PathVariable Long clienteId) {
		return new ResponseEntity<>(service.findAllByClienteId(clienteId), HttpStatus.OK);
	}

	@GetMapping("/activos/{byname}")
	public ResponseEntity<List<Cliente>> findAllActivos(@PathVariable Boolean byname) {
		return new ResponseEntity<>(service.findAllActivos(byname), HttpStatus.OK);
	}

	@GetMapping("/activos2lectura/{zona}/{ruta}")
	public ResponseEntity<List<Cliente>> findAllActivos2Lectura(@PathVariable Integer zona,
			@PathVariable Integer ruta) {
		return new ResponseEntity<>(service.findAllActivos2Lectura(zona, ruta), HttpStatus.OK);
	}

	@GetMapping("/activosbyzona")
	public ResponseEntity<List<Cliente>> findAllActivosByZona() {
		return new ResponseEntity<>(service.findAllActivosByZona(), HttpStatus.OK);
	}

	@GetMapping("/activosbyruta/{zona}")
	public ResponseEntity<List<Cliente>> findAllActivosByRuta(@PathVariable Integer zona) {
		return new ResponseEntity<>(service.findAllActivosByRuta(zona), HttpStatus.OK);
	}

	@GetMapping("/activosmedibles")
	public ResponseEntity<List<Cliente>> findAllActivosMedibles() {
		return new ResponseEntity<>(service.findAllActivosMedibles(), HttpStatus.OK);
	}

	@GetMapping("/activosbyzonaruta/{zona}/{ruta}")
	public ResponseEntity<List<Cliente>> findAllActivosByZonaRuta(@PathVariable Integer zona,
			@PathVariable Integer ruta) {
		return new ResponseEntity<>(service.findAllActivosByZonaRuta(zona, ruta), HttpStatus.OK);
	}

	@GetMapping("/activosbyzonarutaotros/{zona}/{ruta}")
	public ResponseEntity<List<Cliente>> findAllActivosByZonaRutaOtros(@PathVariable Integer zona,
			@PathVariable Integer ruta) {
		return new ResponseEntity<>(service.findAllActivosByZonaRutaOtros(zona, ruta), HttpStatus.OK);
	}

	@GetMapping("/activosconcuotafija")
	public ResponseEntity<List<Cliente>> findAllActivosConCuotaFija() {
		return new ResponseEntity<>(service.findAllActivosConCuotaFija(), HttpStatus.OK);
	}

	@GetMapping("/activosconmedidor")
	public ResponseEntity<List<ActivoConMedidor>> findAllActivosConMedidor() {
		return new ResponseEntity<>(service.findAllActivosConMedidor(), HttpStatus.OK);
	}

	@GetMapping("/zona/{zona}")
	public ResponseEntity<List<Cliente>> findAllActivosZona(@PathVariable Integer zona) {
		return new ResponseEntity<>(service.findAllActivosZona(zona), HttpStatus.OK);
	}

	@GetMapping("/rango/{clienteIddesde}/{clienteIdhasta}")
	public ResponseEntity<List<Cliente>> findAllActivosRango(@PathVariable Long clienteIddesde,
			@PathVariable Long clienteIdhasta) {
		return new ResponseEntity<>(service.findAllActivosRango(clienteIddesde, clienteIdhasta),
				HttpStatus.OK);
	}

	@GetMapping("/sociosactivosconmedidor")
	public ResponseEntity<List<SocioActivoConMedidor>> findAllSociosActivosConMedidor() {
		return new ResponseEntity<>(service.findAllSociosActivosConMedidor(), HttpStatus.OK);
	}

	@GetMapping("/sociosactivos")
	public ResponseEntity<List<SocioActivo>> findAllSociosActivos() {
		return new ResponseEntity<>(service.findAllSociosActivos(), HttpStatus.OK);
	}

	@GetMapping("/sociosactivosconcuotafija")
	public ResponseEntity<List<SocioActivoConCuotaFija>> findAllSociosActivosConCuotaFija() {
		return new ResponseEntity<>(service.findAllSociosActivosConCuotaFija(),
				HttpStatus.OK);
	}

	@GetMapping("/deudoresplancorte")
	public ResponseEntity<List<DeudorPlanCorte>> findAllDeudoresPlanCorte() {
		return new ResponseEntity<>(service.findAllDeudoresPlanCorte(), HttpStatus.OK);
	}

	@GetMapping("/deudoresfactura60dias")
	public ResponseEntity<List<ICliente>> findAllDeudoresFactura60Dias() {
		return new ResponseEntity<>(service.findAllDeudoresFactura60Dias(), HttpStatus.OK);
	}
	
	@GetMapping("/{uniqueId}")
	public ResponseEntity<Cliente> findByUniqueId(@PathVariable Long uniqueId) {
		try {
			return new ResponseEntity<>(service.findByUniqueId(uniqueId), HttpStatus.OK);
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/lastbyclienteId/{clienteId}")
	public ResponseEntity<Cliente> findLastByClienteId(@PathVariable Long clienteId) {
		try {
			return new ResponseEntity<>(service.findLastByClienteId(clienteId), HttpStatus.OK);
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/lastcliente")
	public ResponseEntity<Cliente> findLastCliente() {
		try {
			return new ResponseEntity<>(service.findLastCliente(), HttpStatus.OK);
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/nextbyclienteId/{clienteId}")
	public ResponseEntity<ClienteRecorrido> findNextCliente(@PathVariable Long clienteId) {
		try {
			return new ResponseEntity<>(service.findNextCliente(clienteId), HttpStatus.OK);
		} catch (ClienteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Cliente> add(@RequestBody Cliente cliente) {
		return new ResponseEntity<>(service.add(cliente), HttpStatus.OK);
	}
	
	@PutMapping("/{uniqueId}")
	public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable Long uniqueId) {
		return new ResponseEntity<>(service.update(cliente, uniqueId), HttpStatus.OK);
	}

}
