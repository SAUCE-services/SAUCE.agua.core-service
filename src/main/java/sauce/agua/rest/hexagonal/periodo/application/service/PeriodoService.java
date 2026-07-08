package sauce.agua.rest.hexagonal.periodo.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.periodo.application.exception.PeriodoException;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.in.*;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PeriodoService {

    private final AddPeriodoUseCase addPeriodoUseCase;
    private final GetPeriodoByIdUseCase getPeriodoByIdUseCase;
    private final GetAllPeriodosUseCase getAllPeriodosUseCase;
    private final UpdatePeriodoUseCase updatePeriodoUseCase;
    private final DeletePeriodoUseCase deletePeriodoUseCase;
    private final GetLastPeriodoUseCase getLastPeriodoUseCase;
    private final GetPeriodoByFechaUseCase getPeriodoByFechaUseCase;
    private final GetTodayPeriodoUseCase getTodayPeriodoUseCase;
    private final GetPeriodosRecaudadosUseCase getPeriodosRecaudadosUseCase;

    public List<Periodo> findAll() {
        return getAllPeriodosUseCase.getAllPeriodos();
    }

    public Periodo findByPeriodoId(Integer periodoId) {
        Periodo periodo = getPeriodoByIdUseCase.getPeriodoById(periodoId)
                .orElseThrow(() -> new PeriodoException(periodoId));
        logPeriodo(periodo);
        return periodo;
    }

    public Periodo add(Periodo periodo) {
        log.debug("Processing add");
        logPeriodo(periodo);
        periodo = addPeriodoUseCase.add(periodo);
        return periodo;
    }

    public Periodo update(Periodo newPeriodo, Integer periodoId) {
        log.debug("Processing update");
        Periodo periodo = updatePeriodoUseCase.updatePeriodo(periodoId, newPeriodo)
                .orElseThrow(() -> new PeriodoException(periodoId));
        logPeriodo(periodo);
        return periodo;
    }

    @Transactional
    public void delete(Integer periodoId) {
        log.debug("Processing delete");
        deletePeriodoUseCase.deletePeriodo(periodoId);
    }

    public Periodo findLast() {
        log.debug("Processing findLast");
        Periodo periodo = getLastPeriodoUseCase.getLastPeriodo();
        logPeriodo(periodo);
        return periodo;
    }

    public Periodo findByFecha(OffsetDateTime fecha) {
        log.debug("Processing findByFecha -> {}", fecha);
        Periodo periodo = getPeriodoByFechaUseCase.getPeriodoByFecha(fecha)
                .orElseThrow(PeriodoException::new);
        logPeriodo(periodo);
        return periodo;
    }

    public Periodo findToday() {
        log.debug("Processing findToday");
        Periodo periodo = getTodayPeriodoUseCase.getTodayPeriodo()
                .orElseThrow(PeriodoException::new);
        logPeriodo(periodo);
        return periodo;
    }

    public List<Periodo> findAllRecaudadoByPeriodo(OffsetDateTime desde, OffsetDateTime hasta) {
        log.debug("Processing findAllRecaudadoByPeriodo desde: {} hasta: {}", desde, hasta);
        List<Periodo> periodos = getPeriodosRecaudadosUseCase.getPeriodosRecaudados(desde, hasta);
        periodos.forEach(this::logPeriodo);
        return periodos;
    }

    private void logPeriodo(Periodo periodo) {
        try {
            log.debug("Periodo -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(periodo));
        } catch (JsonProcessingException e) {
            log.debug("Periodo jsonify error -> {}", e.getMessage());
        }
    }
}
