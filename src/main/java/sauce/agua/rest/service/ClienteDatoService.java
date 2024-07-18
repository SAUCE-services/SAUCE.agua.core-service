/**
 * 
 */
package sauce.agua.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.ClienteDatoNotFoundException;
import sauce.agua.rest.model.ClienteDato;
import sauce.agua.rest.repository.IClienteDatoRepository;

/**
 * @author daniel
 *
 */
@Service
public class ClienteDatoService {

	@Autowired
	private IClienteDatoRepository repository;

	public ClienteDato findByClienteId(Long clienteId) {
		return repository.findByClienteId(clienteId).orElseThrow(() -> new ClienteDatoNotFoundException(clienteId));
	}

	public ClienteDato add(ClienteDato clienteDato) {
		return repository.save(clienteDato);
	}

	public ClienteDato update(ClienteDato newClienteDato, Long clienteId) {
		return repository.findByClienteId(clienteId).map(clienteDato -> {
			clienteDato = new ClienteDato(clienteId, newClienteDato.getDocumento(), newClienteDato.getEmail(),
					newClienteDato.getFijo(), newClienteDato.getCelular());
			return repository.save(clienteDato);
		}).orElseThrow(() -> new ClienteDatoNotFoundException(clienteId));
	}

}
