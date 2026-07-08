package sauce.agua.rest.hexagonal.operador.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.GetAllOperadorUseCase;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllOperadorUseCaseImpl implements GetAllOperadorUseCase {

    private final OperadorRepository operadorRepository;

    @Override
    public List<Operador> getAllOperador() {
        return operadorRepository.findAll();
    }
}
