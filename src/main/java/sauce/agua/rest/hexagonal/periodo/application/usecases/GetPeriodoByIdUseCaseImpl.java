package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.GetPeriodoByIdUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPeriodoByIdUseCaseImpl implements GetPeriodoByIdUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public Optional<Periodo> getPeriodoById(Integer id) {
        return periodoRepository.findByPeriodoId(id);
    }
}
