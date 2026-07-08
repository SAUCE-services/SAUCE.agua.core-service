package sauce.agua.rest.hexagonal.medidor.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.medidor.domain.ports.in.FindColocadoMedidorByClienteIdUseCase;
import sauce.agua.rest.hexagonal.medidor.domain.ports.in.FindMedidorByClienteIdUseCase;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedidorService {

    private final FindMedidorByClienteIdUseCase findMedidorByClienteIdUseCase;
    private final FindColocadoMedidorByClienteIdUseCase findColocadoMedidorByClienteIdUseCase;

    public Medidor findByClienteId(Long clienteId, Boolean colocado) {
        Medidor medidor = findMedidorByClienteIdUseCase.findByClienteId(clienteId, colocado);
        logMedidor(medidor);
        return medidor;
    }

    public Medidor findColocadoByClienteId(Long clienteId) {
        Medidor medidor = findColocadoMedidorByClienteIdUseCase.findColocadoByClienteId(clienteId);
        logMedidor(medidor);
        return medidor;
    }

    private void logMedidor(Medidor medidor) {
        try {
            log.debug("Medidor -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(medidor));
        } catch (JsonProcessingException e) {
            log.debug("Medidor jsonify error -> {}", e.getMessage());
        }
    }
}
