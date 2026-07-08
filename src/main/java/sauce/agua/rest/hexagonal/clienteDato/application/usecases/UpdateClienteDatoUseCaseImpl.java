package sauce.agua.rest.hexagonal.clienteDato.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.in.UpdateClienteDatoUseCase;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.out.ClienteDatoRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateClienteDatoUseCaseImpl implements UpdateClienteDatoUseCase {

    private final ClienteDatoRepository clienteDatoRepository;

    @Override
    public Optional<ClienteDato> updateClienteDato(Long clienteId, ClienteDato newClienteDato) {
        return clienteDatoRepository.findByClienteId(clienteId).map(clienteDato -> {
            clienteDato.setDocumento(newClienteDato.getDocumento());
            clienteDato.setEmail(newClienteDato.getEmail());
            clienteDato.setFijo(newClienteDato.getFijo());
            clienteDato.setCelular(newClienteDato.getCelular());
            return clienteDatoRepository.save(clienteDato);
        });
    }
}
