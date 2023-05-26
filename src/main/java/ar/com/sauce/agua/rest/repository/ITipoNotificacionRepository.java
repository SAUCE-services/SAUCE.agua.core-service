/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.TipoNotificacion;

/**
 * @author daniel
 *
 */
@Repository
public interface ITipoNotificacionRepository extends JpaRepository<TipoNotificacion, Integer> {

	public Optional<TipoNotificacion> findByTiponotificacionId(Integer tiponotificacionId);

}
