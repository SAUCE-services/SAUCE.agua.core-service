/**
 * 
 */
package ar.com.sauce.agua.rest.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.com.sauce.agua.rest.exception.MedidorNotFoundException;
import ar.com.sauce.agua.rest.model.Medidor;
import ar.com.sauce.agua.rest.repository.IMedidorRepository;

/**
 * @author daniel
 *
 */
@Service
public class MedidorService {

	@Autowired
	private IMedidorRepository repository;

	public Medidor findByClienteId(Long clienteId, Boolean colocado) {
		Medidor medidor = repository
				.findTopByClienteId(clienteId,
						Sort.by("fechaAlta").descending().and(Sort.by("fechaColocacion").descending()))
				.orElse(new Medidor());
		if (medidor.getFechaRetiro() != null) {
			if (colocado && medidor.getFechaRetiro().compareTo(OffsetDateTime.now()) <= 0) {
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
				.orElseThrow(() -> new MedidorNotFoundException(clienteId, "Colocado"));
	}
}
