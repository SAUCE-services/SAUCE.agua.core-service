/**
 * 
 */
package sauce.agua.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.view.DeudorFactura2;

/**
 * @author daniel
 *
 */
@Repository
public interface IDeudorFactura2Repository extends JpaRepository<DeudorFactura2, Long> {

}
