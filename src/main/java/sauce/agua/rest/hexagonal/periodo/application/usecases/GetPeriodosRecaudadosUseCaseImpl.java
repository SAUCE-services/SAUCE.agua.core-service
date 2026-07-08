package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.GetPeriodosRecaudadosUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPeriodosRecaudadosUseCaseImpl implements GetPeriodosRecaudadosUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public List<Periodo> getPeriodosRecaudados(OffsetDateTime desde, OffsetDateTime hasta) {
        return periodoRepository.findAllRecaudadoByPeriodo(desde, hasta);
    }
}
