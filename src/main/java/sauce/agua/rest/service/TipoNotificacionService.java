/**
 * 
 */
package sauce.agua.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.TipoNotificacionException;
import sauce.agua.rest.model.TipoNotificacion;
import sauce.agua.rest.repository.TipoNotificacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class TipoNotificacionService {

	@Autowired
	private TipoNotificacionRepository repository;

	public List<TipoNotificacion> findAll() {
		return repository.findAll();
	}

	public TipoNotificacion findByTiponotificacionId(Integer tiponotificacionId) {
		return repository.findByTiponotificacionId(tiponotificacionId)
				.orElseThrow(() -> new TipoNotificacionException(tiponotificacionId));
	}
}
