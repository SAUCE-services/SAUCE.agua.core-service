package sauce.agua.rest.hexagonal.operador.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.infrastructure.web.dto.OperadorRequest;
import sauce.agua.rest.hexagonal.operador.infrastructure.web.dto.OperadorResponse;

@Component
public class OperadorDtoMapper {

    public Operador toDomain(OperadorRequest request) {
        if (request == null) {
            return null;
        }
        return Operador.builder()
                .razonSocial(request.getRazonSocial())
                .calle(request.getCalle())
                .puerta(request.getPuerta())
                .piso(request.getPiso())
                .dpto(request.getDpto())
                .codigoPostal(request.getCodigoPostal())
                .localidad(request.getLocalidad())
                .provincia(request.getProvincia())
                .telefono(request.getTelefono())
                .cuit(request.getCuit())
                .ingresosBrutos(request.getIngresosBrutos())
                .situacionIva(request.getSituacionIva())
                .numeroEpas(request.getNumeroEpas())
                .fechaInicio(request.getFechaInicio())
                .servicio(request.getServicio())
                .prefijoId(request.getPrefijoId())
                .facturaId(request.getFacturaId())
                .periodoFactura(request.getPeriodoFactura())
                .resolucion(request.getResolucion())
                .personeria(request.getPersoneria())
                .reciboSerie(request.getReciboSerie())
                .recibo(request.getRecibo())
                .ncreditoSerie(request.getNcreditoSerie())
                .ncredito(request.getNcredito())
                .cai(request.getCai())
                .caiVencimiento(request.getCaiVencimiento())
                .preimpreso(request.getPreimpreso())
                .build();
    }

    public OperadorResponse toResponse(Operador domain) {
        if (domain == null) {
            return null;
        }
        return OperadorResponse.builder()
                .operadorId(domain.getOperadorId())
                .razonSocial(domain.getRazonSocial())
                .calle(domain.getCalle())
                .puerta(domain.getPuerta())
                .piso(domain.getPiso())
                .dpto(domain.getDpto())
                .codigoPostal(domain.getCodigoPostal())
                .localidad(domain.getLocalidad())
                .provincia(domain.getProvincia())
                .telefono(domain.getTelefono())
                .cuit(domain.getCuit())
                .ingresosBrutos(domain.getIngresosBrutos())
                .situacionIva(domain.getSituacionIva())
                .numeroEpas(domain.getNumeroEpas())
                .fechaInicio(domain.getFechaInicio())
                .servicio(domain.getServicio())
                .prefijoId(domain.getPrefijoId())
                .facturaId(domain.getFacturaId())
                .periodoFactura(domain.getPeriodoFactura())
                .resolucion(domain.getResolucion())
                .personeria(domain.getPersoneria())
                .reciboSerie(domain.getReciboSerie())
                .recibo(domain.getRecibo())
                .ncreditoSerie(domain.getNcreditoSerie())
                .ncredito(domain.getNcredito())
                .cai(domain.getCai())
                .caiVencimiento(domain.getCaiVencimiento())
                .preimpreso(domain.getPreimpreso())
                .uid(domain.getUid())
                .build();
    }
}
