/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Medidor;

/**
 * @author daniel
 *
 */
@Repository
public interface MedidorRepository extends JpaRepository<Medidor, Long> {

	public Optional<Medidor> findTopByClienteId(Long clienteId, Sort sort);

	public Optional<Medidor> findTopByClienteIdAndFechaRetiroIsNull(Long clienteId, Sort sort);

}
