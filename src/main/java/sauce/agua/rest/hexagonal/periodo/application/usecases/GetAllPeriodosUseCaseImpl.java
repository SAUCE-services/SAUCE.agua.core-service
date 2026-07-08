package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.GetAllPeriodosUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllPeriodosUseCaseImpl implements GetAllPeriodosUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public List<Periodo> getAllPeriodos() {
        return periodoRepository.findAll();
    }
}
