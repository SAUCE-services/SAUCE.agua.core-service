package sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.dto.PosicionIvaRequest;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.dto.PosicionIvaResponse;

@Component
public class PosicionIvaDtoMapper {

    public PosicionIva toDomain(PosicionIvaRequest request) {
        if (request == null) {
            return null;
        }
        return PosicionIva.builder()
                .nombre(request.getNombre())
                .idPosicionIvaArca(request.getIdPosicionIvaArca())
                .build();
    }

    public PosicionIvaResponse toResponse(PosicionIva domain) {
        if (domain == null) {
            return null;
        }
        return PosicionIvaResponse.builder()
                .posicionId(domain.getPosicionId())
                .nombre(domain.getNombre())
                .idPosicionIvaArca(domain.getIdPosicionIvaArca())
                .build();
    }
}
