package sauce.agua.rest.hexagonal.posicionIva.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.posicionIva.application.exception.PosicionIvaException;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.in.*;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PosicionIvaService {

    private final AddPosicionIvaUseCase addPosicionIvaUseCase;
    private final GetPosicionIvaByIdUseCase getPosicionIvaByIdUseCase;
    private final GetAllPosicionIvaUseCase getAllPosicionIvaUseCase;
    private final UpdatePosicionIvaUseCase updatePosicionIvaUseCase;
    private final DeletePosicionIvaUseCase deletePosicionIvaUseCase;

    public List<PosicionIva> findAll() {
        return getAllPosicionIvaUseCase.getAllPosicionIva();
    }

    public PosicionIva findByPosicionId(Integer posicionId) {
        PosicionIva posicionIva = getPosicionIvaByIdUseCase.getPosicionIvaById(posicionId)
                .orElseThrow(() -> new PosicionIvaException(posicionId));
        logPosicionIva(posicionIva);
        return posicionIva;
    }

    public PosicionIva add(PosicionIva posicionIva) {
        log.debug("Processing add");
        logPosicionIva(posicionIva);
        posicionIva = addPosicionIvaUseCase.add(posicionIva);
        return posicionIva;
    }

    public PosicionIva update(PosicionIva newPosicionIva, Integer posicionId) {
        log.debug("Processing update");
        PosicionIva posicionIva = updatePosicionIvaUseCase.updatePosicionIva(posicionId, newPosicionIva)
                .orElseThrow(() -> new PosicionIvaException(posicionId));
        logPosicionIva(posicionIva);
        return posicionIva;
    }

    @Transactional
    public void delete(Integer posicionId) {
        log.debug("Processing delete");
        deletePosicionIvaUseCase.deletePosicionIva(posicionId);
    }

    private void logPosicionIva(PosicionIva posicionIva) {
        try {
            log.debug("PosicionIva -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(posicionIva));
        } catch (JsonProcessingException e) {
            log.debug("PosicionIva jsonify error -> {}", e.getMessage());
        }
    }
}
