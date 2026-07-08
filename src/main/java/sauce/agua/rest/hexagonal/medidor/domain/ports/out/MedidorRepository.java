package sauce.agua.rest.hexagonal.medidor.domain.ports.out;

import org.springframework.data.domain.Sort;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;

import java.util.Optional;

public interface MedidorRepository {
    Optional<Medidor> findTopByClienteId(Long clienteId, Sort sort);
    Optional<Medidor> findTopByClienteIdAndFechaRetiroIsNull(Long clienteId, Sort sort);
}
