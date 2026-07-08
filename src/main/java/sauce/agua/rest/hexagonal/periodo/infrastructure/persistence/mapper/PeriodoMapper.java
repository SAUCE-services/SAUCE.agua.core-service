package sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.entity.PeriodoEntity;

@Component
public class PeriodoMapper {

    public PeriodoEntity toEntity(Periodo domain) {
        if (domain == null) {
            return null;
        }
        PeriodoEntity entity = new PeriodoEntity();
        entity.setPeriodoId(domain.getPeriodoId());
        entity.setDescripcion(domain.getDescripcion());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        entity.setFechaPrimero(domain.getFechaPrimero());
        entity.setFechaSegundo(domain.getFechaSegundo());
        entity.setTasa(domain.getTasa());
        entity.setLeyenda(domain.getLeyenda());
        entity.setLiquidado(domain.getLiquidado());
        entity.setUid(domain.getUid());
        return entity;
    }

    public Periodo toDomainModel(PeriodoEntity entity) {
        if (entity == null) {
            return null;
        }
        return Periodo.builder()
                .periodoId(entity.getPeriodoId())
                .descripcion(entity.getDescripcion())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .fechaPrimero(entity.getFechaPrimero())
                .fechaSegundo(entity.getFechaSegundo())
                .tasa(entity.getTasa())
                .leyenda(entity.getLeyenda())
                .liquidado(entity.getLiquidado())
                .uid(entity.getUid())
                .build();
    }
}
