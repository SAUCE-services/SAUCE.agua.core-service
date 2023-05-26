/**
 * 
 */
package ar.com.sauce.agua.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.sauce.agua.rest.exception.TipoNotificacionNotFound;
import ar.com.sauce.agua.rest.model.TipoNotificacion;
import ar.com.sauce.agua.rest.repository.ITipoNotificacionRepository;

/**
 * @author daniel
 *
 */
@Service
public class TipoNotificacionService {

	@Autowired
	private ITipoNotificacionRepository repository;

	public List<TipoNotificacion> findAll() {
		return repository.findAll();
	}

	public TipoNotificacion findByTiponotificacionId(Integer tiponotificacionId) {
		return repository.findByTiponotificacionId(tiponotificacionId)
				.orElseThrow(() -> new TipoNotificacionNotFound(tiponotificacionId));
	}
}
