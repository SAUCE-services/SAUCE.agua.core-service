package sauce.agua.rest.hexagonal.clienteDato.domain.ports.in;

import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;

import java.util.Optional;

public interface GetClienteDatoByIdUseCase {
    Optional<ClienteDato> getClienteDatoById(Long clienteId);
}
