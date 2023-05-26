/**
 * 
 */
package ar.com.sauce.agua.rest.repository.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.view.FacturaPmc;

/**
 * @author daniel
 *
 */
@Repository
public interface IFacturaPmcRepository extends JpaRepository<FacturaPmc, Long> {

	public List<FacturaPmc> findAllByFechaprimeroBetween(OffsetDateTime desde, OffsetDateTime hasta);

}
