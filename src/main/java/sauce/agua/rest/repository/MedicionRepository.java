/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Medicion;

/**
 * @author daniel
 *
 */
@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {

	public Optional<Medicion> findByUniqueId(Long uniqueId);

	public Optional<Medicion> findByClienteIdAndPeriodoId(Long clienteId, Integer periodoId);

}
