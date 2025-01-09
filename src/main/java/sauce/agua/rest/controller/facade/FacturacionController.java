/**
 * 
 */
package sauce.agua.rest.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.Factura;
import sauce.agua.rest.service.facade.FacturacionService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/facturacion", "/api/core/facturacion"})
@Slf4j
public class FacturacionController {

	private final FacturacionService service;

	public FacturacionController(FacturacionService service) {
		this.service = service;
	}

	@GetMapping("/ajuste/{prefijoId}/{facturaId}/{decimals}")
	public ResponseEntity<Void> adjust(@PathVariable Integer prefijoId, @PathVariable Long facturaId,
			@PathVariable Integer decimals) {
		service.adjust(prefijoId, facturaId, decimals);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/codigopf")
	public ResponseEntity<String> codigopf(@RequestBody Factura factura) {
		log.debug("Factura -> {}", factura);
		return new ResponseEntity<>(service.recalculateCodigoPagoFacil(factura), HttpStatus.OK);
	}
	
	@GetMapping("/i2of5/{codigo}")
	public ResponseEntity<String> i2of5(@PathVariable String codigo) {
		return new ResponseEntity<>(service.i2of5(codigo), HttpStatus.OK);
	}
}
