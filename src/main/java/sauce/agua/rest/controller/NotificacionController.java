/**
 * 
 */
package sauce.agua.rest.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.Notificacion;
import sauce.agua.rest.service.NotificacionService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/notificacion", "/api/core/notificacion"})
public class NotificacionController {

	private final NotificacionService service;

	public NotificacionController(NotificacionService service) {
		this.service = service;
	}

	@GetMapping("/byfecha/{fecha}")
	public ResponseEntity<List<Notificacion>> findAllByFecha(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return ResponseEntity.ok(service.findAllByFecha(fecha));
	}

	@GetMapping("/unique/{clienteId}/{fecha}")
	public ResponseEntity<Notificacion> findByUnique(@PathVariable Long clienteId,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return ResponseEntity.ok(service.findByUnique(clienteId, fecha));
	}

	@PostMapping("/")
	public ResponseEntity<Notificacion> add(@RequestBody Notificacion notificacion) {
		return ResponseEntity.ok(service.add(notificacion));
	}

	@PutMapping("/{clienteId}/{fecha}")
	public ResponseEntity<Notificacion> update(@RequestBody Notificacion notificacion, @PathVariable Long clienteId,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		return ResponseEntity.ok(service.update(notificacion, clienteId, fecha));
	}
}
