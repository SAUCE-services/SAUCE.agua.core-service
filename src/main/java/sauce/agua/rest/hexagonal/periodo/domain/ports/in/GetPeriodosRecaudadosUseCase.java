package sauce.agua.rest.hexagonal.periodo.domain.ports.in;

import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import java.time.OffsetDateTime;
import java.util.List;

public interface GetPeriodosRecaudadosUseCase {
    List<Periodo> getPeriodosRecaudados(OffsetDateTime desde, OffsetDateTime hasta);
}
