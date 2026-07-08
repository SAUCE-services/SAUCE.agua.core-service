package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.web.dto.TipoNotificacionResponse;

@Component
public class TipoNotificacionDtoMapper {

    public TipoNotificacionResponse toResponse(TipoNotificacion domain) {
        if (domain == null) {
            return null;
        }
        return TipoNotificacionResponse.builder()
                .tiponotificacionId(domain.getTiponotificacionId())
                .nombre(domain.getNombre())
                .valorsocio(domain.getValorsocio())
                .valornosocio(domain.getValornosocio())
                .build();
    }
}
