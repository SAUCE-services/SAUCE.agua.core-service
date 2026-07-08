package sauce.agua.rest.hexagonal.periodo.domain.ports.in;

import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import java.util.List;

public interface GetAllPeriodosUseCase {
    List<Periodo> getAllPeriodos();
}
