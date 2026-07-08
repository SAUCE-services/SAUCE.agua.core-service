package sauce.agua.rest.hexagonal.clienteDato.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.in.AddClienteDatoUseCase;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.out.ClienteDatoRepository;

@Component
@RequiredArgsConstructor
public class AddClienteDatoUseCaseImpl implements AddClienteDatoUseCase {

    private final ClienteDatoRepository clienteDatoRepository;

    @Override
    public ClienteDato add(ClienteDato clienteDato) {
        return clienteDatoRepository.save(clienteDato);
    }
}
