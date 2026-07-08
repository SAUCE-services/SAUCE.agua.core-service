package sauce.agua.rest.hexagonal.medidor.domain.ports.in;

import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;

public interface FindMedidorByClienteIdUseCase {
    Medidor findByClienteId(Long clienteId, Boolean colocado);
}
