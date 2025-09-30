/**
 * 
 */
package sauce.agua.rest.service;

import java.time.OffsetDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.MedidorException;
import sauce.agua.rest.model.Medidor;
import sauce.agua.rest.repository.MedidorRepository;

/**
 * @author daniel
 *
 */
@Service
@RequiredArgsConstructor
public class MedidorService {

	private final MedidorRepository repository;

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
			medidor = findColocadoByClienteId(clienteId);

			if (medidor.getUniqueId() == null) {
				medidor = repository
						.findTopByClienteId(clienteId,
								Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
						.orElse(new Medidor());
			}
		}

		return medidor;

	}

	public Medidor findColocadoByClienteId(Long clienteId) {
		return repository
				.findTopByClienteIdAndFechaRetiroIsNull(clienteId,
						Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
				.orElseThrow(() -> new MedidorException(clienteId, "Colocado"));
	}

}
