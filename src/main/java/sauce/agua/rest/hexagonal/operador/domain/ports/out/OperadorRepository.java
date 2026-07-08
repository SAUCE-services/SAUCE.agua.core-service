package sauce.agua.rest.hexagonal.operador.domain.ports.out;

import sauce.agua.rest.hexagonal.operador.domain.model.Operador;

import java.util.List;
import java.util.Optional;

public interface OperadorRepository {

    List<Operador> findAll();

    Optional<Operador> findByOperadorId(Integer operadorId);

    Operador save(Operador operador);

    void deleteByOperadorId(Integer operadorId);

    Optional<Operador> findTopByOrderByOperadorIdDesc();

}
