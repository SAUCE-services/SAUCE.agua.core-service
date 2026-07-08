package sauce.agua.rest.hexagonal.operador.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sauce.agua.rest.hexagonal.operador.application.service.OperadorService;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.infrastructure.web.dto.OperadorRequest;
import sauce.agua.rest.hexagonal.operador.infrastructure.web.dto.OperadorResponse;
import sauce.agua.rest.hexagonal.operador.infrastructure.web.mapper.OperadorDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/operador", "/api/core/operador"})
@Slf4j
@RequiredArgsConstructor
public class OperadorController {

    private final OperadorService service;
    private final OperadorDtoMapper dtoMapper;

    @GetMapping("/")
    public ResponseEntity<List<OperadorResponse>> findAll() {
        List<OperadorResponse> responses = service.findAll().stream()
                .map(dtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/last")
    public ResponseEntity<OperadorResponse> findLast() {
        OperadorResponse response = dtoMapper.toResponse(service.findLast());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{operadorId}")
    public ResponseEntity<OperadorResponse> findByOperadorId(@PathVariable Integer operadorId) {
        OperadorResponse response = dtoMapper.toResponse(service.findByOperadorId(operadorId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<OperadorResponse> add(@RequestBody OperadorRequest request) {
        Operador domain = dtoMapper.toDomain(request);
        OperadorResponse response = dtoMapper.toResponse(service.add(domain));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{operadorId}")
    public ResponseEntity<OperadorResponse> update(@RequestBody OperadorRequest request, @PathVariable Integer operadorId) {
        Operador domain = dtoMapper.toDomain(request);
        OperadorResponse response = dtoMapper.toResponse(service.update(domain, operadorId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{operadorId}")
    public ResponseEntity<Void> delete(@PathVariable Integer operadorId) {
        service.delete(operadorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
