package sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.in;

import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import java.util.List;

public interface GetAllTipoNotificacionUseCase {
    List<TipoNotificacion> getAllTipoNotificacion();
}
