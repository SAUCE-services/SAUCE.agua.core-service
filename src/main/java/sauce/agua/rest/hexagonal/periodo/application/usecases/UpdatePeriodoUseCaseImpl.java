package sauce.agua.rest.hexagonal.periodo.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.UpdatePeriodoUseCase;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdatePeriodoUseCaseImpl implements UpdatePeriodoUseCase {

    private final PeriodoRepository periodoRepository;

    @Override
    public Optional<Periodo> updatePeriodo(Integer id, Periodo newPeriodo) {
        return periodoRepository.findByPeriodoId(id).map(periodo -> {
            periodo.setDescripcion(newPeriodo.getDescripcion());
            periodo.setFechaInicio(newPeriodo.getFechaInicio());
            periodo.setFechaFin(newPeriodo.getFechaFin());
            periodo.setFechaPrimero(newPeriodo.getFechaPrimero());
            periodo.setFechaSegundo(newPeriodo.getFechaSegundo());
            periodo.setTasa(newPeriodo.getTasa());
            periodo.setLeyenda(newPeriodo.getLeyenda());
            periodo.setLiquidado(newPeriodo.getLiquidado());
            periodo.setUid(newPeriodo.getUid());
            return periodoRepository.save(periodo);
        });
    }
}
