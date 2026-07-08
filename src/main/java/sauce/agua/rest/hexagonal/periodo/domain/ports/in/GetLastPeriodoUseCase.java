package sauce.agua.rest.hexagonal.periodo.domain.ports.in;

import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;

public interface GetLastPeriodoUseCase {
    Periodo getLastPeriodo();
}
