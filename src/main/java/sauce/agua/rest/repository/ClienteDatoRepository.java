/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.ClienteDato;

/**
 * @author daniel
 *
 */
@Repository
public interface ClienteDatoRepository extends JpaRepository<ClienteDato, Long> {

	Optional<ClienteDato> findByClienteId(Long clienteId);

}
