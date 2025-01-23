/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.TipoNotificacion;

/**
 * @author daniel
 *
 */
@Repository
public interface TipoNotificacionRepository extends JpaRepository<TipoNotificacion, Integer> {

	public Optional<TipoNotificacion> findByTiponotificacionId(Integer tiponotificacionId);

}
