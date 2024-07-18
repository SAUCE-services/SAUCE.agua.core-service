/**
 * 
 */
package sauce.agua.rest.controller.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.view.FacturaPmc;
import sauce.agua.rest.service.view.FacturaPmcService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/facturapmc")
public class FacturaPmcController {
	@Autowired
	private FacturaPmcService service;

	@GetMapping("/deuda/{desde}/{hasta}")
	public ResponseEntity<List<FacturaPmc>> findAllByPeriodo(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime desde,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime hasta) {
		return new ResponseEntity<List<FacturaPmc>>(service.findAllByPeriodo(desde, hasta), HttpStatus.OK);
	}
}
