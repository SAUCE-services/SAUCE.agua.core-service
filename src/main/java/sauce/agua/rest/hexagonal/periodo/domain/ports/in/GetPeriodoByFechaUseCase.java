package sauce.agua.rest.hexagonal.periodo.domain.ports.in;

import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface GetPeriodoByFechaUseCase {
    Optional<Periodo> getPeriodoByFecha(OffsetDateTime fecha);
}
