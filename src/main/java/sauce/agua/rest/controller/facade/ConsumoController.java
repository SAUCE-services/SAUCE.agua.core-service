package sauce.agua.rest.controller.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sauce.agua.rest.model.internal.DatoConsumo;
import sauce.agua.rest.service.facade.ConsumoService;

import java.time.OffsetDateTime;

@RestController
@RequestMapping({"/consumo", "/api/core/consumo"})
@Slf4j
public class ConsumoController {

    private final ConsumoService service;

    public ConsumoController(ConsumoService service) {
        this.service = service;
    }

    @GetMapping("/calculate/{clienteId}/{periodoId}/{medidorId}/{fechaEmision}")
    public ResponseEntity<DatoConsumo> calculateConsumo(@PathVariable Long clienteId, @PathVariable Integer periodoId, @PathVariable String medidorId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fechaEmision) {
        log.debug("Processing controller calculateConsumo clienteId={}, periodoId={}, medidorId={}, fechaEmision={}", clienteId, periodoId, medidorId, fechaEmision);
        return ResponseEntity.ok(service.calculateConsumo(clienteId, periodoId, medidorId, fechaEmision));
    }

}
