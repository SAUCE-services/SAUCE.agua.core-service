package sauce.agua.rest.controller.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sauce.agua.rest.model.internal.FacturacionDto;
import sauce.agua.rest.service.extern.FacturacionElectronicaService;
import sauce.agua.rest.service.facade.FacturaPdfService;

@RestController
@RequestMapping({"/makeFactura", "/api/core/makeFactura"})
@Slf4j
@RequiredArgsConstructor
public class MakeFacturaController {

    private final FacturaPdfService facturaPdfService;
    private final FacturacionElectronicaService facturacionElectronicaService;

//    @GetMapping("/pdf/{prefijoId}/{facturaId}")
//    public ResponseEntity<Resource> makePdf(@PathVariable Integer prefijoId, @PathVariable Long facturaId) throws FileNotFoundException {
//        String filename = facturaPdfService.generatePdf(prefijoId, facturaId);
//        File file = new File(filename);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura.pdf");
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//        return ResponseEntity.ok().headers(headers).contentLength(file.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
//    }

    @PostMapping("/afip/make")
    public ResponseEntity<FacturacionDto> makeFactura(@RequestBody FacturacionDto facturacionDto) {
        log.debug("Processing request for makeFactura");
        return new ResponseEntity<>(facturacionElectronicaService.makeFactura(facturacionDto), HttpStatus.OK);
    }

}
