package sauce.agua.rest.hexagonal.medidor.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.medidor.infrastructure.web.dto.MedidorResponse;

@Component
public class MedidorDtoMapper {

    public MedidorResponse toResponse(Medidor domain) {
        if (domain == null) {
            return null;
        }
        return MedidorResponse.builder()
                .uniqueId(domain.getUniqueId())
                .medidorId(domain.getMedidorId())
                .fechaAlta(domain.getFechaAlta())
                .clienteId(domain.getClienteId())
                .fechaColocacion(domain.getFechaColocacion())
                .fechaRetiro(domain.getFechaRetiro())
                .motivoRetiro(domain.getMotivoRetiro())
                .estadoInicio(domain.getEstadoInicio())
                .build();
    }
}
