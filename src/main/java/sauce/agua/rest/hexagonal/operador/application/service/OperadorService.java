package sauce.agua.rest.hexagonal.operador.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.operador.application.exception.OperadorException;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.*;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperadorService {

    private final AddOperadorUseCase addOperadorUseCase;
    private final GetOperadorByIdUseCase getOperadorByIdUseCase;
    private final GetAllOperadorUseCase getAllOperadorUseCase;
    private final UpdateOperadorUseCase updateOperadorUseCase;
    private final DeleteOperadorUseCase deleteOperadorUseCase;
    private final GetLastOperadorUseCase getLastOperadorUseCase;

    public List<Operador> findAll() {
        return getAllOperadorUseCase.getAllOperador();
    }

    public Operador findByOperadorId(Integer operadorId) {
        Operador operador = getOperadorByIdUseCase.getOperadorById(operadorId)
                .orElseThrow(() -> new OperadorException(operadorId));
        logOperador(operador);
        return operador;
    }

    public Operador add(Operador operador) {
        log.debug("Processing add");
        logOperador(operador);
        operador = addOperadorUseCase.add(operador);
        return operador;
    }

    public Operador update(Operador newOperador, Integer operadorId) {
        log.debug("Processing update");
        Operador operador = updateOperadorUseCase.updateOperador(operadorId, newOperador)
                .orElseThrow(() -> new OperadorException(operadorId));
        logOperador(operador);
        return operador;
    }

    public Operador findLast() {
        log.debug("Processing findLast");
        Operador operador = getLastOperadorUseCase.getLastOperador();
        logOperador(operador);
        return operador;
    }

    @Transactional
    public void delete(Integer operadorId) {
        log.debug("Processing delete");
        deleteOperadorUseCase.deleteOperador(operadorId);
    }

    private void logOperador(Operador operador) {
        try {
            log.debug("Operador -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(operador));
        } catch (JsonProcessingException e) {
            log.debug("Operador jsonify error -> {}", e.getMessage());
        }
    }
}
