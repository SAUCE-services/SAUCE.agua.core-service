/**
 * 
 */
package sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.entity.PeriodoEntity;

/**
 * @author daniel
 *
 */
@Repository
public interface JpaPeriodoRepository extends JpaRepository<PeriodoEntity, Integer> {

	Optional<PeriodoEntity> findByPeriodoId(Integer periodoId);

	@Modifying
	void deleteByPeriodoId(Integer periodoId);

	Optional<PeriodoEntity> findTopByOrderByPeriodoIdDesc();

	Optional<PeriodoEntity> findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(
			OffsetDateTime fecha_less, OffsetDateTime fecha_greater);

	Optional<PeriodoEntity> findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(OffsetDateTime today);

}
