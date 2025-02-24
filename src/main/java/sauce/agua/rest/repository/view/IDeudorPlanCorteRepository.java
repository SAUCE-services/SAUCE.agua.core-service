/**
 * 
 */
package sauce.agua.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.view.DeudorPlanCorte;

/**
 * @author daniel
 *
 */
@Repository
public interface IDeudorPlanCorteRepository extends JpaRepository<DeudorPlanCorte, Long> {

}
