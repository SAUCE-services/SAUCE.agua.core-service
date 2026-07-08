package sauce.agua.rest.hexagonal.periodo.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sauce.agua.rest.hexagonal.periodo.application.exception.PeriodoException;
import sauce.agua.rest.hexagonal.periodo.application.service.PeriodoService;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.infrastructure.web.dto.PeriodoRequest;
import sauce.agua.rest.hexagonal.periodo.infrastructure.web.dto.PeriodoResponse;
import sauce.agua.rest.hexagonal.periodo.infrastructure.web.mapper.PeriodoDtoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/periodo", "/api/core/periodo"})
@Slf4j
@RequiredArgsConstructor
public class PeriodoController {

    private final PeriodoService service;
    private final PeriodoDtoMapper dtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<PeriodoResponse>> findAll() {
        List<PeriodoResponse> responses = service.findAll().stream()
                .map(dtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/recaudado/{desde}/{hasta}")
    public ResponseEntity<List<PeriodoResponse>> findAllRecaudadoByPeriodo(
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime desde,
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime hasta) {
        List<PeriodoResponse> responses = service.findAllRecaudadoByPeriodo(desde, hasta).stream()
                .map(dtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{periodoId}")
    public ResponseEntity<PeriodoResponse> findByPeriodoId(@PathVariable Integer periodoId) {
        PeriodoResponse response = dtoMapper.toResponse(service.findByPeriodoId(periodoId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/last")
    public ResponseEntity<PeriodoResponse> findLast() {
        PeriodoResponse response = dtoMapper.toResponse(service.findLast());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/today")
    public ResponseEntity<PeriodoResponse> findToday() {
        PeriodoResponse response = dtoMapper.toResponse(service.findToday());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/byfecha/{fecha}")
    public ResponseEntity<PeriodoResponse> findByFecha(
            @PathVariable @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime fecha) {
        try {
            PeriodoResponse response = dtoMapper.toResponse(service.findByFecha(fecha));
            return ResponseEntity.ok(response);
        } catch (PeriodoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<PeriodoResponse> add(@RequestBody PeriodoRequest request) {
        Periodo domain = dtoMapper.toDomain(request);
        PeriodoResponse response = dtoMapper.toResponse(service.add(domain));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{periodoId}")
    public ResponseEntity<PeriodoResponse> update(@RequestBody PeriodoRequest request, @PathVariable Integer periodoId) {
        Periodo domain = dtoMapper.toDomain(request);
        PeriodoResponse response = dtoMapper.toResponse(service.update(domain, periodoId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{periodoId}")
    public ResponseEntity<Void> delete(@PathVariable Integer periodoId) {
        service.delete(periodoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
