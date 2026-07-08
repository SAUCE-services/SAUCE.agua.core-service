package sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.out;

import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import java.util.List;
import java.util.Optional;

public interface TipoNotificacionRepository {
    List<TipoNotificacion> findAll();
    Optional<TipoNotificacion> findByTiponotificacionId(Integer tiponotificacionId);
}
