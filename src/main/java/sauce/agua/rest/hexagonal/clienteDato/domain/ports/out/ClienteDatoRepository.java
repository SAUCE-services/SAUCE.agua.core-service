package sauce.agua.rest.hexagonal.clienteDato.domain.ports.out;

import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;

import java.util.Optional;

public interface ClienteDatoRepository {

    Optional<ClienteDato> findByClienteId(Long clienteId);

    ClienteDato save(ClienteDato clienteDato);

}
