package sauce.agua.rest.hexagonal.clienteDato.domain.ports.in;

import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;

public interface AddClienteDatoUseCase {
    ClienteDato add(ClienteDato clienteDato);
}
