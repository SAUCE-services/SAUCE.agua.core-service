/**
 * 
 */
package sauce.agua.rest.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.NotificacionException;
import sauce.agua.rest.model.Notificacion;
import sauce.agua.rest.repository.NotificacionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class NotificacionService {

	@Autowired
	private NotificacionRepository repository;

	public List<Notificacion> findAllByFecha(OffsetDateTime fecha) {
		return repository.findAllByFecha(fecha);
	}

	public Notificacion findByUnique(Long clienteId, OffsetDateTime fecha) {
		return repository.findByClienteIdAndFecha(clienteId, fecha)
				.orElseThrow(() -> new NotificacionException(clienteId, fecha));
	}

	public Notificacion add(Notificacion notificacion) {
		repository.save(notificacion);
		log.debug("Notificacion -> " + notificacion);
		return null;
	}

	public Notificacion update(Notificacion newnotificacion, Long clienteId, OffsetDateTime fecha) {
		return repository.findByClienteIdAndFecha(clienteId, fecha).map(notificacion -> {
			notificacion = new Notificacion(clienteId, fecha, newnotificacion.getNotificacionId(),
					newnotificacion.getTiponotificacionId(), newnotificacion.getVencimiento(),
					newnotificacion.getEstado());
			repository.save(notificacion);
			log.debug("Notificacion -> " + notificacion);
			return notificacion;
		}).orElseThrow(() -> new NotificacionException(clienteId, fecha));
	}

}
