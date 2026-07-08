package sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sauce.agua.rest.hexagonal.posicionIva.application.service.PosicionIvaService;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.dto.PosicionIvaRequest;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.dto.PosicionIvaResponse;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.mapper.PosicionIvaDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/posicionIva", "/api/core/posicionIva"})
@Slf4j
@RequiredArgsConstructor
public class PosicionIvaController {

    private final PosicionIvaService service;
    private final PosicionIvaDtoMapper dtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<PosicionIvaResponse>> findAll() {
        List<PosicionIvaResponse> responses = service.findAll().stream()
                .map(dtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{posicionId}")
    public ResponseEntity<PosicionIvaResponse> findByPosicionId(@PathVariable Integer posicionId) {
        PosicionIvaResponse response = dtoMapper.toResponse(service.findByPosicionId(posicionId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<PosicionIvaResponse> add(@RequestBody PosicionIvaRequest request) {
        PosicionIva domain = dtoMapper.toDomain(request);
        PosicionIvaResponse response = dtoMapper.toResponse(service.add(domain));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{posicionId}")
    public ResponseEntity<PosicionIvaResponse> update(@RequestBody PosicionIvaRequest request, @PathVariable Integer posicionId) {
        PosicionIva domain = dtoMapper.toDomain(request);
        PosicionIvaResponse response = dtoMapper.toResponse(service.update(domain, posicionId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{posicionId}")
    public ResponseEntity<Void> delete(@PathVariable Integer posicionId) {
        service.delete(posicionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
