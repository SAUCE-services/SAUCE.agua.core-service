package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.DeletePeriodoUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

@Component
@RequiredArgsConstructor
public class DeletePeriodoUseCaseImpl implements DeletePeriodoUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public void deletePeriodo(Integer id) {
        periodoRepository.deleteByPeriodoId(id);
    }
}
