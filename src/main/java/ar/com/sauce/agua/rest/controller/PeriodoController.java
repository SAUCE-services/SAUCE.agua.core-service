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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.sauce.agua.rest.model.Periodo;
import ar.com.sauce.agua.rest.service.PeriodoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/periodo")
@Slf4j
public class PeriodoController {
	@Autowired
	private PeriodoService service;

	@GetMapping("/")
	public ResponseEntity<List<Periodo>> findAll() {
		return new ResponseEntity<List<Periodo>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/recaudado/{desde}/{hasta}")
	public ResponseEntity<List<Periodo>> findAllRecaudadoByPeriodo(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime desde,
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime hasta) {
		return new ResponseEntity<List<Periodo>>(service.findAllRecaudadoByPeriodo(desde, hasta), HttpStatus.OK);
	}

	@GetMapping("/{periodoId}")
	public ResponseEntity<Periodo> findByPeriodoId(@PathVariable Integer periodoId) {
		return new ResponseEntity<Periodo>(service.findByPeriodoId(periodoId), HttpStatus.OK);
	}

	@GetMapping("/last")
	public ResponseEntity<Periodo> findLast() {
		return new ResponseEntity<Periodo>(service.findLast(), HttpStatus.OK);
	}

	@GetMapping("/today")
	public ResponseEntity<Periodo> findToday() {
		return new ResponseEntity<Periodo>(service.findToday(), HttpStatus.OK);
	}

	@GetMapping("/byfecha/{fecha}")
	public ResponseEntity<Periodo> findByFecha(
			@PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
		log.debug("Controller -> " + fecha.toString());
		return new ResponseEntity<Periodo>(service.findByFecha(fecha), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Periodo> add(@RequestBody Periodo periodo) {
		return new ResponseEntity<Periodo>(service.add(periodo), HttpStatus.OK);
	}

	@PutMapping("/{periodoId}")
	public ResponseEntity<Periodo> update(@RequestBody Periodo periodo, @PathVariable Integer periodoId) {
		return new ResponseEntity<Periodo>(service.update(periodo, periodoId), HttpStatus.OK);
	}

	@DeleteMapping("/{periodoId}")
	public ResponseEntity<Void> delete(@PathVariable Integer periodoId) {
		service.delete(periodoId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
