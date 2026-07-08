package sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.dto.ClienteDatoRequest;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.dto.ClienteDatoResponse;

@Component
public class ClienteDatoDtoMapper {

    public ClienteDato toDomain(ClienteDatoRequest request) {
        if (request == null) {
            return null;
        }
        return ClienteDato.builder()
                .clienteId(request.getClienteId())
                .documento(request.getDocumento())
                .email(request.getEmail())
                .fijo(request.getFijo())
                .celular(request.getCelular())
                .build();
    }

    public ClienteDatoResponse toResponse(ClienteDato domain) {
        if (domain == null) {
            return null;
        }
        return ClienteDatoResponse.builder()
                .clienteId(domain.getClienteId())
                .documento(domain.getDocumento())
                .email(domain.getEmail())
                .fijo(domain.getFijo())
                .celular(domain.getCelular())
                .build();
    }
}
