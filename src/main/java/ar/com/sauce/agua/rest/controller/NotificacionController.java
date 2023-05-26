/**
 * 
 */
package ar.com.sauce.agua.rest.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.sauce.agua.rest.model.Notificacion;
import ar.com.sauce.agua.rest.service.NotificacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

	@Autowired
	private NotificacionService service;

	@GetMapping("/byfecha/{fecha}")
	public ResponseEntity<List<Notificacion>> findAllByFecha(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return new ResponseEntity<List<Notificacion>>(service.findAllByFecha(fecha), HttpStatus.OK);
	}

	@GetMapping("/unique/{clienteId}/{fecha}")
	public ResponseEntity<Notificacion> findByUnique(@PathVariable Long clienteId,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return new ResponseEntity<Notificacion>(service.findByUnique(clienteId, fecha), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Notificacion> add(@RequestBody Notificacion notificacion) {
		return new ResponseEntity<Notificacion>(service.add(notificacion), HttpStatus.OK);
	}

	@PutMapping("/{clienteId}/{fecha}")
	public ResponseEntity<Notificacion> update(@RequestBody Notificacion notificacion, @PathVariable Long clienteId,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return new ResponseEntity<Notificacion>(service.update(notificacion, clienteId, fecha), HttpStatus.OK);
	}
}
