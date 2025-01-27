package sauce.agua.rest.controller.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sauce.agua.rest.model.internal.ConsumoContextDto;
import sauce.agua.rest.model.internal.DatoConsumo;
import sauce.agua.rest.service.facade.ConsumoService;

@RestController
@RequestMapping({"/consumo", "/api/core/consumo"})
@Slf4j
public class ConsumoController {

    private final ConsumoService service;

    public ConsumoController(ConsumoService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public ResponseEntity<DatoConsumo> calculateConsumo(@RequestBody ConsumoContextDto consumoContext) {
        log.debug("Processing controller calculateConsumo clienteId={}, periodoId={}, medidorId={}, fechaEmision={}", consumoContext.getClienteId(), consumoContext.getPeriodoId(), consumoContext.getMedidorId(), consumoContext.getFechaEmision());
        return ResponseEntity.ok(service.calculateConsumo(consumoContext.getClienteId(), consumoContext.getPeriodoId(), consumoContext.getMedidorId(), consumoContext.getFechaEmision()));
    }

}
