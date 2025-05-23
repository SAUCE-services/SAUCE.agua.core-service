/**
 * 
 */
package sauce.agua.rest.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Desconexion;

/**
 * @author daniel
 *
 */
@Repository
public interface DesconexionRepository extends JpaRepository<Desconexion, Long> {

	public Optional<Desconexion> findTopByClienteIdAndFechaDesconexionLessThanEqualOrderByFechaDesconexionDesc(Long clienteId,
			OffsetDateTime fechaEmision);

}
