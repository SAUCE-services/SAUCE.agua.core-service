/**
 * 
 */
package ar.com.sauce.agua.rest.repository.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.view.PeriodoPago;
import ar.com.sauce.agua.rest.model.view.pk.PeriodoPagoPk;

/**
 * @author daniel
 *
 */
@Repository
public interface IPeriodoPagoRepository extends JpaRepository<PeriodoPago, PeriodoPagoPk> {

	public List<PeriodoPago> findAllByFechapagoBetween(OffsetDateTime desde, OffsetDateTime hasta, Sort sort);

}
