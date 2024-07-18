/**
 * 
 */
package sauce.agua.rest.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.DesconexionNotFoundException;
import sauce.agua.rest.model.Desconexion;
import sauce.agua.rest.repository.IDesconexionRepository;

/**
 * @author daniel
 *
 */
@Service
public class DesconexionService {

	@Autowired
	private IDesconexionRepository repository;

	public Desconexion findByClienteId(Long clienteId, OffsetDateTime fechaEmision) {
		return repository
				.findTopByClienteIdAndFechaDesconexionLessThanEqualOrderByFechaDesconexionDesc(clienteId, fechaEmision)
				.orElseThrow(() -> new DesconexionNotFoundException(clienteId, fechaEmision));
	}

}
