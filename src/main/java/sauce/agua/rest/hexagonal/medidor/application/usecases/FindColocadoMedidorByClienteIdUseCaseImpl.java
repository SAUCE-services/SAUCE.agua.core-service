package sauce.agua.rest.hexagonal.medidor.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.medidor.application.exception.MedidorException;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.medidor.domain.ports.in.FindColocadoMedidorByClienteIdUseCase;
import sauce.agua.rest.hexagonal.medidor.domain.ports.out.MedidorRepository;

@Component
@RequiredArgsConstructor
public class FindColocadoMedidorByClienteIdUseCaseImpl implements FindColocadoMedidorByClienteIdUseCase {

    private final MedidorRepository repository;

    @Override
    public Medidor findColocadoByClienteId(Long clienteId) {
        return repository
                .findTopByClienteIdAndFechaRetiroIsNull(clienteId,
                        Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
                .orElseThrow(() -> new MedidorException(clienteId, "Colocado"));
    }
}
