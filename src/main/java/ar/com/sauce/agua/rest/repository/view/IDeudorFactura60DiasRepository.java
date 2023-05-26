/**
 * 
 */
package ar.com.sauce.agua.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.view.DeudorFactura60Dias;

/**
 * @author daniel
 *
 */
@Repository
public interface IDeudorFactura60DiasRepository extends JpaRepository<DeudorFactura60Dias, Long> {

}
