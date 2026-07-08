package sauce.agua.rest.hexagonal.periodo.domain.ports.in;

import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import java.util.Optional;

public interface GetTodayPeriodoUseCase {
    Optional<Periodo> getTodayPeriodo();
}
