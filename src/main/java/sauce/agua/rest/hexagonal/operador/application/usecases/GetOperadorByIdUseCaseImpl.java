package sauce.agua.rest.hexagonal.operador.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.GetOperadorByIdUseCase;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetOperadorByIdUseCaseImpl implements GetOperadorByIdUseCase {

    private final OperadorRepository operadorRepository;

    @Override
    public Optional<Operador> getOperadorById(Integer id) {
        return operadorRepository.findByOperadorId(id);
    }
}
