/**
 * 
 */
package sauce.agua.rest.repository.view;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.view.ClienteRecorrido;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteRecorridoRepository extends JpaRepository<ClienteRecorrido, Long> {

	public Optional<ClienteRecorrido> findByClienteId(Long clienteId);

	public Optional<ClienteRecorrido> findFirstByRecorridoGreaterThan(Long recorrido);

}
