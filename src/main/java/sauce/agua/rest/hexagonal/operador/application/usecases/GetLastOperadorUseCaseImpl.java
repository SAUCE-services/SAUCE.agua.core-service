package sauce.agua.rest.hexagonal.operador.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.GetLastOperadorUseCase;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;

@Component
@RequiredArgsConstructor
public class GetLastOperadorUseCaseImpl implements GetLastOperadorUseCase {

    private final OperadorRepository operadorRepository;

    @Override
    public Operador getLastOperador() {
        return operadorRepository.findTopByOrderByOperadorIdDesc().orElse(new Operador());
    }
}
