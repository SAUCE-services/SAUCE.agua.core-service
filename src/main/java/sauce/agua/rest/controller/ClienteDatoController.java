/**
 * 
 */
package sauce.agua.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import sauce.agua.rest.exception.ClienteDatoNotFoundException;
import sauce.agua.rest.model.ClienteDato;
import sauce.agua.rest.service.ClienteDatoService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/clientedato")
public class ClienteDatoController {

	@Autowired
	private ClienteDatoService service;

	@GetMapping("/{clienteId}")
	public ResponseEntity<ClienteDato> findByClienteId(@PathVariable Long clienteId) {
		try {
			return new ResponseEntity<ClienteDato>(service.findByClienteId(clienteId), HttpStatus.OK);
		} catch (ClienteDatoNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/")
	public ResponseEntity<ClienteDato> add(@RequestBody ClienteDato clienteDato) {
		return new ResponseEntity<ClienteDato>(service.add(clienteDato), HttpStatus.OK);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<ClienteDato> update(@RequestBody ClienteDato clienteDato, @PathVariable Long clienteId) {
		return new ResponseEntity<ClienteDato>(service.update(clienteDato, clienteId), HttpStatus.OK);
	}

}