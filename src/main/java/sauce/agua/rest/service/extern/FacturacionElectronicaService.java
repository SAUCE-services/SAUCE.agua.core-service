package sauce.agua.rest.service.extern;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.extern.client.afip.FacturacionAfipClient;
import sauce.agua.rest.hexagonal.clienteDato.application.exception.ClienteDatoException;
import sauce.agua.rest.hexagonal.clienteDato.application.service.ClienteDatoService;
import sauce.agua.rest.model.internal.FacturacionDto;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacturacionElectronicaService {

    private final FacturacionAfipClient facturacionAfipClient;
    private final ClienteDatoService clienteDatoService;

    public FacturacionDto makeFactura(FacturacionDto facturacionDto) {
        log.debug("Processing FacturacionElectronicaService. makeFactura");
        log.debug("FacturacionDto: {}", facturacionDto.jsonify());
        // Intentando determinar numero y tipo de documento
        if (facturacionDto.getDocumento().equals("0")) {
            try {
                var clienteDato = clienteDatoService.findByClienteId(facturacionDto.getClienteId());
                if (clienteDato.getDocumento().compareTo(BigDecimal.ZERO) > 0) {
                    facturacionDto.setTipoDocumento(96);
                    var documento = clienteDato.getDocumento().toPlainString();
                    facturacionDto.setDocumento(documento.substring(Math.max(0, documento.length() - 8)));
                }
            } catch (ClienteDatoException e) {
                log.error("Error al obtener el documento de la factura " + e.getMessage());
            }
        }
        //
        log.debug("Calling facturador");
        facturacionDto = facturacionAfipClient.facturador(facturacionDto);
        log.debug("Resultado facturador");
        log.debug("FacturacionDto: {}", facturacionDto.jsonify());
        return facturacionDto;
    }

}
