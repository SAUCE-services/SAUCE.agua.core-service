package sauce.agua.rest.hexagonal.clienteDato.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.clienteDato.application.exception.ClienteDatoException;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.in.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteDatoService {

    private final AddClienteDatoUseCase addClienteDatoUseCase;
    private final GetClienteDatoByIdUseCase getClienteDatoByIdUseCase;
    private final UpdateClienteDatoUseCase updateClienteDatoUseCase;

    public ClienteDato findByClienteId(Long clienteId) {
        ClienteDato clienteDato = getClienteDatoByIdUseCase.getClienteDatoById(clienteId)
                .orElseThrow(() -> new ClienteDatoException(clienteId));
        logClienteDato(clienteDato);
        return clienteDato;
    }

    public ClienteDato add(ClienteDato clienteDato) {
        log.debug("Processing add");
        logClienteDato(clienteDato);
        clienteDato = addClienteDatoUseCase.add(clienteDato);
        return clienteDato;
    }

    public ClienteDato update(ClienteDato newClienteDato, Long clienteId) {
        log.debug("Processing update");
        ClienteDato clienteDato = updateClienteDatoUseCase.updateClienteDato(clienteId, newClienteDato)
                .orElseThrow(() -> new ClienteDatoException(clienteId));
        logClienteDato(clienteDato);
        return clienteDato;
    }

    private void logClienteDato(ClienteDato clienteDato) {
        try {
            log.debug("ClienteDato -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(clienteDato));
        } catch (JsonProcessingException e) {
            log.debug("ClienteDato jsonify error -> {}", e.getMessage());
        }
    }
}
