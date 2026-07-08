package sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.in;

import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import java.util.Optional;

public interface GetTipoNotificacionByIdUseCase {
    Optional<TipoNotificacion> getTipoNotificacionById(Integer tiponotificacionId);
}
