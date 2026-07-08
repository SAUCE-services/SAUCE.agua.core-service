package sauce.agua.rest.hexagonal.operador.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.infrastructure.persistence.entity.OperadorEntity;

@Component
public class OperadorMapper {

    public OperadorEntity toEntity(Operador domain) {
        if (domain == null) {
            return null;
        }
        OperadorEntity entity = new OperadorEntity();
        entity.setOperadorId(domain.getOperadorId());
        entity.setRazonSocial(domain.getRazonSocial());
        entity.setCalle(domain.getCalle());
        entity.setPuerta(domain.getPuerta());
        entity.setPiso(domain.getPiso());
        entity.setDpto(domain.getDpto());
        entity.setCodigoPostal(domain.getCodigoPostal());
        entity.setLocalidad(domain.getLocalidad());
        entity.setProvincia(domain.getProvincia());
        entity.setTelefono(domain.getTelefono());
        entity.setCuit(domain.getCuit());
        entity.setIngresosBrutos(domain.getIngresosBrutos());
        entity.setSituacionIva(domain.getSituacionIva());
        entity.setNumeroEpas(domain.getNumeroEpas());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setServicio(domain.getServicio());
        entity.setPrefijoId(domain.getPrefijoId());
        entity.setFacturaId(domain.getFacturaId());
        entity.setPeriodoFactura(domain.getPeriodoFactura());
        entity.setResolucion(domain.getResolucion());
        entity.setPersoneria(domain.getPersoneria());
        entity.setReciboSerie(domain.getReciboSerie());
        entity.setRecibo(domain.getRecibo());
        entity.setNcreditoSerie(domain.getNcreditoSerie());
        entity.setNcredito(domain.getNcredito());
        entity.setCai(domain.getCai());
        entity.setCaiVencimiento(domain.getCaiVencimiento());
        entity.setPreimpreso(domain.getPreimpreso());
        entity.setUid(domain.getUid());
        return entity;
    }

    public Operador toDomainModel(OperadorEntity entity) {
        if (entity == null) {
            return null;
        }
        return Operador.builder()
                .operadorId(entity.getOperadorId())
                .razonSocial(entity.getRazonSocial())
                .calle(entity.getCalle())
                .puerta(entity.getPuerta())
                .piso(entity.getPiso())
                .dpto(entity.getDpto())
                .codigoPostal(entity.getCodigoPostal())
                .localidad(entity.getLocalidad())
                .provincia(entity.getProvincia())
                .telefono(entity.getTelefono())
                .cuit(entity.getCuit())
                .ingresosBrutos(entity.getIngresosBrutos())
                .situacionIva(entity.getSituacionIva())
                .numeroEpas(entity.getNumeroEpas())
                .fechaInicio(entity.getFechaInicio())
                .servicio(entity.getServicio())
                .prefijoId(entity.getPrefijoId())
                .facturaId(entity.getFacturaId())
                .periodoFactura(entity.getPeriodoFactura())
                .resolucion(entity.getResolucion())
                .personeria(entity.getPersoneria())
                .reciboSerie(entity.getReciboSerie())
                .recibo(entity.getRecibo())
                .ncreditoSerie(entity.getNcreditoSerie())
                .ncredito(entity.getNcredito())
                .cai(entity.getCai())
                .caiVencimiento(entity.getCaiVencimiento())
                .preimpreso(entity.getPreimpreso())
                .uid(entity.getUid())
                .build();
    }
}
