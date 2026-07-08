package sauce.agua.rest.hexagonal.operador.domain.ports.in;

import sauce.agua.rest.hexagonal.operador.domain.model.Operador;

import java.util.Optional;

public interface UpdateOperadorUseCase {
    Optional<Operador> updateOperador(Integer id, Operador operador);
}
