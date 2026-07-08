package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.GetPeriodoByFechaUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPeriodoByFechaUseCaseImpl implements GetPeriodoByFechaUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public Optional<Periodo> getPeriodoByFecha(OffsetDateTime fecha) {
        return periodoRepository.findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(fecha, fecha);
    }
}
