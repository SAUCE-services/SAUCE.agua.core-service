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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.TipoNotificacion;
import sauce.agua.rest.service.TipoNotificacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/tiponotificacion", "/api/core/tiponotificacion"})
public class TipoNotificacionController {

	private final TipoNotificacionService service;

	public TipoNotificacionController(TipoNotificacionService service) {
		this.service = service;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TipoNotificacion>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{tiponotificacionId}")
	public ResponseEntity<TipoNotificacion> findByTiponotificacionId(@PathVariable Integer tiponotificacionId) {
		return new ResponseEntity<>(service.findByTiponotificacionId(tiponotificacionId), HttpStatus.OK);
	}
}
