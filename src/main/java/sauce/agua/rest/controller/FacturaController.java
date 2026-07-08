/**
 * 
 */
package sauce.agua.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.Factura;
import sauce.agua.rest.service.FacturaService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/factura", "/api/core/factura"})
public class FacturaController {

	private final FacturaService service;

	public FacturaController(FacturaService service) {
		this.service = service;
	}

	@GetMapping("/periodo/{periodoId}")
	public ResponseEntity<List<Factura>> findAllByPeriodoId(@PathVariable Integer periodoId) {
		return ResponseEntity.ok(service.findAllByPeriodoId(periodoId));
	}

	@GetMapping("/zona/{periodoId}/{zona}")
	public ResponseEntity<List<Factura>> findAllByPeriodoIdAndZona(@PathVariable Integer periodoId,
			@PathVariable Integer zona) {
		return ResponseEntity.ok(service.findAllByPeriodoIdAndZona(periodoId, zona));
	}

	@GetMapping("/{prefijoId}/{facturaId}")
	public ResponseEntity<Factura> findByFactura(@PathVariable Integer prefijoId, @PathVariable Long facturaId) {
		return ResponseEntity.ok(service.findByFactura(prefijoId, facturaId));
	}

	@PostMapping("/deuda/clientes/{periodoId}")
	public ResponseEntity<List<Factura>> findAllDeudaByPeriodoIdAndClienteIds(
			@PathVariable Integer periodoId,
			@RequestBody List<Long> clienteIds) {
		return ResponseEntity.ok(
			service.findAllDeudaByPeriodoIdAndClienteIds(periodoId, clienteIds));
	}

	@PostMapping("/uniques")
	public ResponseEntity<List<Factura>> findAllByUniqueIdIn(
			@RequestBody List<Long> uniqueIds) {
		return ResponseEntity.ok(
			service.findAllByUniqueIdIn(uniqueIds));
	}

	@GetMapping("/deuda/print/{clienteId}/{periodoIdReferencia}")
	public ResponseEntity<List<Factura>> findAllByDeudaPrint(
			@PathVariable Long clienteId,
			@PathVariable Integer periodoIdReferencia) {
		return ResponseEntity.ok(
			service.findAllByDeudaPrint(clienteId, periodoIdReferencia));
	}

}
