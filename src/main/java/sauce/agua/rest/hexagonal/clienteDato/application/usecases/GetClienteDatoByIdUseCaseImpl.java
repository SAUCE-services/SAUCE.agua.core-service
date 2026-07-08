package sauce.agua.rest.hexagonal.clienteDato.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.in.GetClienteDatoByIdUseCase;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.out.ClienteDatoRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetClienteDatoByIdUseCaseImpl implements GetClienteDatoByIdUseCase {

    private final ClienteDatoRepository clienteDatoRepository;

    @Override
    public Optional<ClienteDato> getClienteDatoById(Long clienteId) {
        return clienteDatoRepository.findByClienteId(clienteId);
    }
}
