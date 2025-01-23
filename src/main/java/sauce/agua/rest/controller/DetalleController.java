package sauce.agua.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sauce.agua.rest.model.Detalle;
import sauce.agua.rest.service.DetalleService;

import java.util.List;

@RestController
@RequestMapping({"/detalle", "/api/core/detalle"})
public class DetalleController {

    private final DetalleService service;

    public DetalleController(DetalleService service) {
        this.service = service;
    }

    @GetMapping("/factura/{prefijoId}/{facturaId}")
    public ResponseEntity<List<Detalle>> findAllByFactura(@PathVariable Integer prefijoId, @PathVariable Long facturaId) {
        return new ResponseEntity<>(service.findAllByFactura(prefijoId, facturaId), HttpStatus.OK);
    }

}
