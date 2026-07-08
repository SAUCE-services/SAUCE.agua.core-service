package sauce.agua.rest.hexagonal.operador.domain.ports.in;

import sauce.agua.rest.hexagonal.operador.domain.model.Operador;

public interface AddOperadorUseCase {
    Operador add(Operador operador);
}
