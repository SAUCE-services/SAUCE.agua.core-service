package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.AddPeriodoUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

@Component
@RequiredArgsConstructor
public class AddPeriodoUseCaseImpl implements AddPeriodoUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public Periodo add(Periodo periodo) {
        return periodoRepository.save(periodo);
    }
}
