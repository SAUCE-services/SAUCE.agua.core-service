package sauce.agua.rest.hexagonal.tipoNotificacion.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.tipoNotificacion.application.exception.TipoNotificacionException;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.in.GetAllTipoNotificacionUseCase;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.in.GetTipoNotificacionByIdUseCase;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TipoNotificacionService {

    private final GetAllTipoNotificacionUseCase getAllTipoNotificacionUseCase;
    private final GetTipoNotificacionByIdUseCase getTipoNotificacionByIdUseCase;

    public List<TipoNotificacion> findAll() {
        return getAllTipoNotificacionUseCase.getAllTipoNotificacion();
    }

    public TipoNotificacion findByTiponotificacionId(Integer tiponotificacionId) {
        TipoNotificacion tipoNotificacion = getTipoNotificacionByIdUseCase.getTipoNotificacionById(tiponotificacionId)
                .orElseThrow(() -> new TipoNotificacionException(tiponotificacionId));
        logTipoNotificacion(tipoNotificacion);
        return tipoNotificacion;
    }

    private void logTipoNotificacion(TipoNotificacion tipoNotificacion) {
        try {
            log.debug("TipoNotificacion -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(tipoNotificacion));
        } catch (JsonProcessingException e) {
            log.debug("TipoNotificacion jsonify error -> {}", e.getMessage());
        }
    }
}
