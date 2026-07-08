package sauce.agua.rest.hexagonal.medidor.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sauce.agua.rest.hexagonal.medidor.application.service.MedidorService;
import sauce.agua.rest.hexagonal.medidor.infrastructure.web.dto.MedidorResponse;
import sauce.agua.rest.hexagonal.medidor.infrastructure.web.mapper.MedidorDtoMapper;

@RestController
@RequestMapping({"/medidor", "/api/core/medidor"})
@Slf4j
@RequiredArgsConstructor
public class MedidorController {

    private final MedidorService service;
    private final MedidorDtoMapper dtoMapper;

    @GetMapping("/cliente/{clienteId}/{colocado}")
    public ResponseEntity<MedidorResponse> findByClienteId(
            @PathVariable Long clienteId,
            @PathVariable Boolean colocado) {
        MedidorResponse response = dtoMapper.toResponse(service.findByClienteId(clienteId, colocado));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/colocado/{clienteId}")
    public ResponseEntity<MedidorResponse> findColocadoByClienteId(
            @PathVariable Long clienteId) {
        MedidorResponse response = dtoMapper.toResponse(service.findColocadoByClienteId(clienteId));
        return ResponseEntity.ok(response);
    }
}
