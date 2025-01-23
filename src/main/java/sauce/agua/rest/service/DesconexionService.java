/**
 * 
 */
package sauce.agua.rest.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.DesconexionException;
import sauce.agua.rest.model.Desconexion;
import sauce.agua.rest.repository.DesconexionRepository;

/**
 * @author daniel
 *
 */
@Service
public class DesconexionService {

	@Autowired
	private DesconexionRepository repository;

	public Desconexion findByClienteId(Long clienteId, OffsetDateTime fechaEmision) {
		return repository
				.findTopByClienteIdAndFechaDesconexionLessThanEqualOrderByFechaDesconexionDesc(clienteId, fechaEmision)
				.orElseThrow(() -> new DesconexionException(clienteId, fechaEmision));
	}

}
