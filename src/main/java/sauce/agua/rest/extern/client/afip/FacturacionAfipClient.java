package sauce.agua.rest.extern.client.afip;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sauce.agua.rest.model.internal.FacturacionDto;

@FeignClient("pyafipws-service/api/afipws")
public interface FacturacionAfipClient {

    @GetMapping("/test")
    String test();

    @PostMapping("/facturador")
    FacturacionDto facturador(FacturacionDto facturacionDto);

}
