package sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.entity.ClienteDatoEntity;

@Component
public class ClienteDatoMapper {

    public ClienteDatoEntity toEntity(ClienteDato domain) {
        if (domain == null) {
            return null;
        }
        ClienteDatoEntity entity = new ClienteDatoEntity();
        entity.setClienteId(domain.getClienteId());
        entity.setDocumento(domain.getDocumento());
        entity.setEmail(domain.getEmail());
        entity.setFijo(domain.getFijo());
        entity.setCelular(domain.getCelular());
        return entity;
    }

    public ClienteDato toDomainModel(ClienteDatoEntity entity) {
        if (entity == null) {
            return null;
        }
        return ClienteDato.builder()
                .clienteId(entity.getClienteId())
                .documento(entity.getDocumento())
                .email(entity.getEmail())
                .fijo(entity.getFijo())
                .celular(entity.getCelular())
                .build();
    }
}
