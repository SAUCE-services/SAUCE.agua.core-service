/**
 * 
 */
package sauce.agua.rest.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Periodo;

/**
 * @author daniel
 *
 */
@Repository
public interface IPeriodoRepository extends JpaRepository<Periodo, Integer> {

	public Optional<Periodo> findByPeriodoId(Integer periodoId);

	@Modifying
	public void deleteByPeriodoId(Integer periodoId);

	public Optional<Periodo> findTopByOrderByPeriodoIdDesc();

	public Optional<Periodo> findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(
			OffsetDateTime fecha_less, OffsetDateTime fecha_greater);

	public Optional<Periodo> findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(OffsetDateTime today);

}
