package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sauce.agua.rest.hexagonal.tipoNotificacion.application.service.TipoNotificacionService;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.web.dto.TipoNotificacionResponse;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.web.mapper.TipoNotificacionDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/tiponotificacion", "/api/core/tiponotificacion"})
@Slf4j
@RequiredArgsConstructor
public class TipoNotificacionController {

    private final TipoNotificacionService service;
    private final TipoNotificacionDtoMapper dtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<TipoNotificacionResponse>> findAll() {
        List<TipoNotificacionResponse> responses = service.findAll().stream()
                .map(dtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{tiponotificacionId}")
    public ResponseEntity<TipoNotificacionResponse> findByTiponotificacionId(@PathVariable Integer tiponotificacionId) {
        TipoNotificacionResponse response = dtoMapper.toResponse(service.findByTiponotificacionId(tiponotificacionId));
        return ResponseEntity.ok(response);
    }
}
