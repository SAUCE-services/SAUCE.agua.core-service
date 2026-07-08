package sauce.agua.rest.hexagonal.clienteDato.domain.ports.in;

import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;

import java.util.Optional;

public interface UpdateClienteDatoUseCase {
    Optional<ClienteDato> updateClienteDato(Long clienteId, ClienteDato newClienteDato);
}
