package sauce.agua.rest.hexagonal.periodo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.infrastructure.web.dto.PeriodoRequest;
import sauce.agua.rest.hexagonal.periodo.infrastructure.web.dto.PeriodoResponse;

@Component
public class PeriodoDtoMapper {

    public Periodo toDomain(PeriodoRequest request) {
        if (request == null) {
            return null;
        }
        return Periodo.builder()
                .descripcion(request.getDescripcion())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .fechaPrimero(request.getFechaPrimero())
                .fechaSegundo(request.getFechaSegundo())
                .tasa(request.getTasa())
                .leyenda(request.getLeyenda())
                .liquidado(request.getLiquidado())
                .uid(request.getUid())
                .build();
    }

    public PeriodoResponse toResponse(Periodo domain) {
        if (domain == null) {
            return null;
        }
        return PeriodoResponse.builder()
                .periodoId(domain.getPeriodoId())
                .descripcion(domain.getDescripcion())
                .fechaInicio(domain.getFechaInicio())
                .fechaFin(domain.getFechaFin())
                .fechaPrimero(domain.getFechaPrimero())
                .fechaSegundo(domain.getFechaSegundo())
                .tasa(domain.getTasa())
                .leyenda(domain.getLeyenda())
                .liquidado(domain.getLiquidado())
                .uid(domain.getUid())
                .build();
    }
}
