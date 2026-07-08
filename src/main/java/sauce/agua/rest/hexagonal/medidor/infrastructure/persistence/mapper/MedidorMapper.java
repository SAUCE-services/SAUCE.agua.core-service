package sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.entity.MedidorEntity;

@Component
public class MedidorMapper {

    public MedidorEntity toEntity(Medidor domain) {
        if (domain == null) {
            return null;
        }
        MedidorEntity entity = new MedidorEntity();
        entity.setUniqueId(domain.getUniqueId());
        entity.setMedidorId(domain.getMedidorId());
        entity.setFechaAlta(domain.getFechaAlta());
        entity.setClienteId(domain.getClienteId());
        entity.setFechaColocacion(domain.getFechaColocacion());
        entity.setFechaRetiro(domain.getFechaRetiro());
        entity.setMotivoRetiro(domain.getMotivoRetiro());
        entity.setEstadoInicio(domain.getEstadoInicio());
        return entity;
    }

    public Medidor toDomainModel(MedidorEntity entity) {
        if (entity == null) {
            return null;
        }
        return Medidor.builder()
                .uniqueId(entity.getUniqueId())
                .medidorId(entity.getMedidorId())
                .fechaAlta(entity.getFechaAlta())
                .clienteId(entity.getClienteId())
                .fechaColocacion(entity.getFechaColocacion())
                .fechaRetiro(entity.getFechaRetiro())
                .motivoRetiro(entity.getMotivoRetiro())
                .estadoInicio(entity.getEstadoInicio())
                .build();
    }
}
