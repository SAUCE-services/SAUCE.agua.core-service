package sauce.agua.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sauce.agua.rest.model.Medidor;
import sauce.agua.rest.service.MedidorService;

@RestController
@RequestMapping({"/medidor", "/api/core/medidor"})
public class MedidorController {

    private final MedidorService service;

    public MedidorController(MedidorService service) {
        this.service = service;
    }

    @GetMapping("/cliente/{clienteId}/{colocado}")
    public ResponseEntity<Medidor> findByClienteId(
            @PathVariable Long clienteId,
            @PathVariable Boolean colocado) {
        return new ResponseEntity<>(
            service.findByClienteId(clienteId, colocado), 
            HttpStatus.OK);
    }

    @GetMapping("/cliente/colocado/{clienteId}")
    public ResponseEntity<Medidor> findColocadoByClienteId(
            @PathVariable Long clienteId) {
        return new ResponseEntity<>(
            service.findColocadoByClienteId(clienteId), 
            HttpStatus.OK);
    }

}
