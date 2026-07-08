package sauce.agua.rest.hexagonal.medidor.domain.ports.in;

import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;

public interface FindColocadoMedidorByClienteIdUseCase {
    Medidor findColocadoByClienteId(Long clienteId);
}
