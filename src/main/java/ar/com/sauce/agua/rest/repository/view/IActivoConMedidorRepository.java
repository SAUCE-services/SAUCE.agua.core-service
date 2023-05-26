/**
 * 
 */
package ar.com.sauce.agua.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.view.ActivoConMedidor;

/**
 * @author daniel
 *
 */
@Repository
public interface IActivoConMedidorRepository extends JpaRepository<ActivoConMedidor, Long> {

}
