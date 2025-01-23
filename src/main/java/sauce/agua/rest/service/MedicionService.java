/**
 * 
 */
package sauce.agua.rest.service;

import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.MedicionException;
import sauce.agua.rest.model.Medicion;
import sauce.agua.rest.repository.MedicionRepository;

/**
 * @author daniel
 *
 */
@Service
public class MedicionService {

	private final MedicionRepository repository;

	public MedicionService(MedicionRepository repository) {
		this.repository = repository;
	}

	public Medicion findByClienteId(Long clienteId, Integer periodoId) {
		return repository.findByClienteIdAndPeriodoId(clienteId, periodoId)
				.orElseThrow(() -> new MedicionException(clienteId, periodoId));
	}

	public Medicion add(Medicion medicion) {
		return repository.save(medicion);
	}

	public Medicion update(Medicion newMedicion, Long uniqueId) {
		return repository.findByUniqueId(uniqueId).map(medicion -> {
			medicion = new Medicion(uniqueId, newMedicion.getClienteId(), newMedicion.getPeriodoId(),
					newMedicion.getMedidorId(), newMedicion.getFechaLectura(), newMedicion.getEstado());
			return medicion;
		}).orElseThrow(() -> new MedicionException(uniqueId));
	}

}
