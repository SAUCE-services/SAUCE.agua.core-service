package sauce.agua.rest.hexagonal.operador.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.DeleteOperadorUseCase;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;

@Component
@RequiredArgsConstructor
public class DeleteOperadorUseCaseImpl implements DeleteOperadorUseCase {

    private final OperadorRepository operadorRepository;

    @Override
    public void deleteOperador(Integer id) {
        operadorRepository.deleteByOperadorId(id);
    }
}
