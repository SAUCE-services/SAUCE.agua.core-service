package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.entity.TipoNotificacionEntity;

@Component
public class TipoNotificacionMapper {

    public TipoNotificacionEntity toEntity(TipoNotificacion domain) {
        if (domain == null) {
            return null;
        }
        TipoNotificacionEntity entity = new TipoNotificacionEntity();
        entity.setTiponotificacionId(domain.getTiponotificacionId());
        entity.setNombre(domain.getNombre());
        entity.setValorsocio(domain.getValorsocio());
        entity.setValornosocio(domain.getValornosocio());
        return entity;
    }

    public TipoNotificacion toDomainModel(TipoNotificacionEntity entity) {
        if (entity == null) {
            return null;
        }
        return TipoNotificacion.builder()
                .tiponotificacionId(entity.getTiponotificacionId())
                .nombre(entity.getNombre())
                .valorsocio(entity.getValorsocio())
                .valornosocio(entity.getValornosocio())
                .build();
    }
}
