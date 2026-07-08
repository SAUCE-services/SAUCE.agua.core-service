package sauce.agua.rest.hexagonal.medidor.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.medidor.application.exception.MedidorException;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.medidor.domain.ports.in.FindMedidorByClienteIdUseCase;
import sauce.agua.rest.hexagonal.medidor.domain.ports.out.MedidorRepository;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class FindMedidorByClienteIdUseCaseImpl implements FindMedidorByClienteIdUseCase {

    private final MedidorRepository repository;

    @Override
    public Medidor findByClienteId(Long clienteId, Boolean colocado) {
        Medidor medidor = repository
                .findTopByClienteId(clienteId,
                        Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
                .orElse(new Medidor());
        if (medidor.getFechaRetiro() != null) {
            if (colocado && !medidor.getFechaRetiro().isAfter(OffsetDateTime.now())) {
                return new Medidor();
            }
        }
        if (colocado && medidor.getFechaRetiro() != null) {
            medidor = repository
                    .findTopByClienteIdAndFechaRetiroIsNull(clienteId,
                            Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
                    .orElseThrow(() -> new MedidorException(clienteId, "Colocado"));

            if (medidor.getUniqueId() == null) {
                medidor = repository
                        .findTopByClienteId(clienteId,
                                Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
                        .orElse(new Medidor());
            }
        }

        return medidor;
    }
}
