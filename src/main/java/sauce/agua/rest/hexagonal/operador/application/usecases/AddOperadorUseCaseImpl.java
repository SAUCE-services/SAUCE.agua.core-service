package sauce.agua.rest.hexagonal.operador.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.AddOperadorUseCase;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;

@Component
@RequiredArgsConstructor
public class AddOperadorUseCaseImpl implements AddOperadorUseCase {

    private final OperadorRepository operadorRepository;

    @Override
    public Operador add(Operador operador) {
        return operadorRepository.save(operador);
    }
}
