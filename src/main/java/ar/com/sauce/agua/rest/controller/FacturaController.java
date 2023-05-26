/**
 * 
 */
package ar.com.sauce.agua.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.sauce.agua.rest.model.Factura;
import ar.com.sauce.agua.rest.service.FacturaService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/factura")
public class FacturaController {
	@Autowired
	private FacturaService service;

	@GetMapping("/periodo/{periodoId}")
	public ResponseEntity<List<Factura>> findAllByPeriodoId(@PathVariable Integer periodoId) {
		return new ResponseEntity<List<Factura>>(service.findAllByPeriodoId(periodoId), HttpStatus.OK);
	}

	@GetMapping("/zona/{periodoId}/{zona}")
	public ResponseEntity<List<Factura>> findAllByPeriodoIdAndZona(@PathVariable Integer periodoId,
			@PathVariable Integer zona) {
		return new ResponseEntity<List<Factura>>(service.findAllByPeriodoIdAndZona(periodoId, zona), HttpStatus.OK);
	}

	@GetMapping("/{prefijoId}/{facturaId}")
	public ResponseEntity<Factura> findByFactura(@PathVariable Integer prefijoId, @PathVariable Long facturaId) {
		return new ResponseEntity<Factura>(service.findByFactura(prefijoId, facturaId), HttpStatus.OK);
	}
}
