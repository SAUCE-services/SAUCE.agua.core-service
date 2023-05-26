/**
 * 
 */
package ar.com.sauce.agua.rest.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.sauce.agua.rest.exception.NotificacionNotFoundException;
import ar.com.sauce.agua.rest.model.Notificacion;
import ar.com.sauce.agua.rest.repository.INotificacionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class NotificacionService {

	@Autowired
	private INotificacionRepository repository;

	public List<Notificacion> findAllByFecha(OffsetDateTime fecha) {
		return repository.findAllByFecha(fecha);
	}

	public Notificacion findByUnique(Long clienteId, OffsetDateTime fecha) {
		return repository.findByClienteIdAndFecha(clienteId, fecha)
				.orElseThrow(() -> new NotificacionNotFoundException(clienteId, fecha));
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
		}).orElseThrow(() -> new NotificacionNotFoundException(clienteId, fecha));
	}

}
