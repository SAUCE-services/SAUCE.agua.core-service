package sauce.agua.rest.hexagonal.periodo.domain.ports.out;

import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PeriodoRepository {

    List<Periodo> findAll();

    Optional<Periodo> findByPeriodoId(Integer periodoId);

    Periodo save(Periodo periodo);

    void deleteByPeriodoId(Integer periodoId);

    Optional<Periodo> findTopByOrderByPeriodoIdDesc();

    Optional<Periodo> findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(
            OffsetDateTime fechaLess, OffsetDateTime fechaGreater);

    Optional<Periodo> findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(OffsetDateTime today);

    List<Periodo> findAllRecaudadoByPeriodo(OffsetDateTime desde, OffsetDateTime hasta);
}
