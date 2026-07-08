package sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.entity.PosicionIvaEntity;

@Component
public class PosicionIvaMapper {

    public PosicionIvaEntity toEntity(PosicionIva domain) {
        if (domain == null) {
            return null;
        }
        PosicionIvaEntity entity = new PosicionIvaEntity();
        entity.setPosicionId(domain.getPosicionId());
        entity.setNombre(domain.getNombre());
        entity.setIdPosicionIvaArca(domain.getIdPosicionIvaArca());
        return entity;
    }

    public PosicionIva toDomainModel(PosicionIvaEntity entity) {
        if (entity == null) {
            return null;
        }
        return PosicionIva.builder()
                .posicionId(entity.getPosicionId())
                .nombre(entity.getNombre())
                .idPosicionIvaArca(entity.getIdPosicionIvaArca())
                .build();
    }
}
