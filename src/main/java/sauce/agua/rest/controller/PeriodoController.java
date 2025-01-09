/**
 * 
 */
package sauce.agua.rest.controller;

import java.time.OffsetDateTime;
import java.util.List;

import sauce.agua.rest.exception.PeriodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.Periodo;
import sauce.agua.rest.service.PeriodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping({"/periodo", "/api/core/periodo"})
@Slf4j
public class PeriodoController {

	private final PeriodoService service;

	public PeriodoController(PeriodoService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<Periodo>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/recaudado/{desde}/{hasta}")
	public ResponseEntity<List<Periodo>> findAllRecaudadoByPeriodo(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime desde,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime hasta) {
		return new ResponseEntity<>(service.findAllRecaudadoByPeriodo(desde, hasta), HttpStatus.OK);
	}

	@GetMapping("/{periodoId}")
	public ResponseEntity<Periodo> findByPeriodoId(@PathVariable Integer periodoId) {
		return new ResponseEntity<>(service.findByPeriodoId(periodoId), HttpStatus.OK);
	}

	@GetMapping("/last")
	public ResponseEntity<Periodo> findLast() {
		return new ResponseEntity<>(service.findLast(), HttpStatus.OK);
	}

	@GetMapping("/today")
	public ResponseEntity<Periodo> findToday() {
		return new ResponseEntity<Periodo>(service.findToday(), HttpStatus.OK);
	}

	@GetMapping("/byfecha/{fecha}")
	public ResponseEntity<Periodo> findByFecha(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		try {
			return new ResponseEntity<>(service.findByFecha(fecha), HttpStatus.OK);
		} catch (PeriodoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<Periodo> add(@RequestBody Periodo periodo) {
		return new ResponseEntity<>(service.add(periodo), HttpStatus.OK);
	}

	@PutMapping("/{periodoId}")
	public ResponseEntity<Periodo> update(@RequestBody Periodo periodo, @PathVariable Integer periodoId) {
		return new ResponseEntity<>(service.update(periodo, periodoId), HttpStatus.OK);
	}

	@DeleteMapping("/{periodoId}")
	public ResponseEntity<Void> delete(@PathVariable Integer periodoId) {
		service.delete(periodoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
