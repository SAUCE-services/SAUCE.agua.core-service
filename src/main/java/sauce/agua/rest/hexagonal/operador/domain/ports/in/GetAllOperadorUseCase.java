package sauce.agua.rest.hexagonal.operador.domain.ports.in;

import sauce.agua.rest.hexagonal.operador.domain.model.Operador;

import java.util.List;

public interface GetAllOperadorUseCase {
    List<Operador> getAllOperador();
}
