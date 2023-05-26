/**
 * 
 */
package ar.com.sauce.agua.rest.controller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.sauce.agua.rest.model.Factura;
import ar.com.sauce.agua.rest.service.facade.FacturacionService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/facturacion")
@Slf4j
public class FacturacionController {
	@Autowired
	private FacturacionService service;

	@GetMapping("/ajuste/{prefijoId}/{facturaId}/{decimals}")
	public ResponseEntity<Void> adjust(@PathVariable Integer prefijoId, @PathVariable Long facturaId,
			@PathVariable Integer decimals) {
		service.adjust(prefijoId, facturaId, decimals);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/codigopf")
	public ResponseEntity<String> codigopf(@RequestBody Factura factura) {
		log.debug("Factura -> {}", factura);
		return new ResponseEntity<String>(service.recalculateCodigoPagoFacil(factura), HttpStatus.OK);
	}
	
	@GetMapping("/i2of5/{codigo}")
	public ResponseEntity<String> i2of5(@PathVariable String codigo) {
		return new ResponseEntity<String>(service.i2of5(codigo), HttpStatus.OK);
	}
}
