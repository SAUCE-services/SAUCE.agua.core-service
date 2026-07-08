package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.GetTodayPeriodoUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetTodayPeriodoUseCaseImpl implements GetTodayPeriodoUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public Optional<Periodo> getTodayPeriodo() {
        OffsetDateTime today = OffsetDateTime.now(ZoneId.of("UTC")).minusHours(3);
        return periodoRepository.findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(today);
    }
}
