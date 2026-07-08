package sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import sauce.agua.rest.hexagonal.clienteDato.application.exception.ClienteDatoException;
import sauce.agua.rest.hexagonal.clienteDato.application.service.ClienteDatoService;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.dto.ClienteDatoRequest;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.dto.ClienteDatoResponse;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.mapper.ClienteDatoDtoMapper;

@RestController
@RequestMapping({"/clientedato", "/api/core/clientedato"})
@Slf4j
@RequiredArgsConstructor
public class ClienteDatoController {

    private final ClienteDatoService service;
    private final ClienteDatoDtoMapper dtoMapper;

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDatoResponse> findByClienteId(@PathVariable Long clienteId) {
        try {
            ClienteDatoResponse response = dtoMapper.toResponse(service.findByClienteId(clienteId));
            return ResponseEntity.ok(response);
        } catch (ClienteDatoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<ClienteDatoResponse> add(@RequestBody ClienteDatoRequest request) {
        ClienteDato domain = dtoMapper.toDomain(request);
        ClienteDatoResponse response = dtoMapper.toResponse(service.add(domain));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteDatoResponse> update(@RequestBody ClienteDatoRequest request, @PathVariable Long clienteId) {
        ClienteDato domain = dtoMapper.toDomain(request);
        ClienteDatoResponse response = dtoMapper.toResponse(service.update(domain, clienteId));
        return ResponseEntity.ok(response);
    }
}
