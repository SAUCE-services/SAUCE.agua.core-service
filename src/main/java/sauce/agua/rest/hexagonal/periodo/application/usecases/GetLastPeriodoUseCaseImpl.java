package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.GetLastPeriodoUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

@Component
@RequiredArgsConstructor
public class GetLastPeriodoUseCaseImpl implements GetLastPeriodoUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public Periodo getLastPeriodo() {
        return periodoRepository.findTopByOrderByPeriodoIdDesc().orElse(new Periodo());
    }
}
