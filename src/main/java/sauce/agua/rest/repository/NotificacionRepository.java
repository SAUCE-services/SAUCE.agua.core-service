/**
 * 
 */
package sauce.agua.rest.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Notificacion;
import sauce.agua.rest.model.pk.NotificacionPk;

/**
 * @author daniel
 *
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, NotificacionPk> {

	public List<Notificacion> findAllByFecha(OffsetDateTime fecha);

	public Optional<Notificacion> findByClienteIdAndFecha(Long clienteId, OffsetDateTime fecha);

}
