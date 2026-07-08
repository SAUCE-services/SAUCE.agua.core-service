package sauce.agua.rest.hexagonal.operador.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.in.UpdateOperadorUseCase;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateOperadorUseCaseImpl implements UpdateOperadorUseCase {

    private final OperadorRepository operadorRepository;

    @Override
    public Optional<Operador> updateOperador(Integer id, Operador newOperador) {
        return operadorRepository.findByOperadorId(id).map(operador -> {
            operador.setRazonSocial(newOperador.getRazonSocial());
            operador.setCalle(newOperador.getCalle());
            operador.setPuerta(newOperador.getPuerta());
            operador.setPiso(newOperador.getPiso());
            operador.setDpto(newOperador.getDpto());
            operador.setCodigoPostal(newOperador.getCodigoPostal());
            operador.setLocalidad(newOperador.getLocalidad());
            operador.setProvincia(newOperador.getProvincia());
            operador.setTelefono(newOperador.getTelefono());
            operador.setCuit(newOperador.getCuit());
            operador.setIngresosBrutos(newOperador.getIngresosBrutos());
            operador.setSituacionIva(newOperador.getSituacionIva());
            operador.setNumeroEpas(newOperador.getNumeroEpas());
            operador.setFechaInicio(newOperador.getFechaInicio());
            operador.setServicio(newOperador.getServicio());
            operador.setPrefijoId(newOperador.getPrefijoId());
            operador.setFacturaId(newOperador.getFacturaId());
            operador.setPeriodoFactura(newOperador.getPeriodoFactura());
            operador.setResolucion(newOperador.getResolucion());
            operador.setPersoneria(newOperador.getPersoneria());
            operador.setReciboSerie(newOperador.getReciboSerie());
            operador.setRecibo(newOperador.getRecibo());
            operador.setNcreditoSerie(newOperador.getNcreditoSerie());
            operador.setNcredito(newOperador.getNcredito());
            operador.setCai(newOperador.getCai());
            operador.setCaiVencimiento(newOperador.getCaiVencimiento());
            operador.setPreimpreso(newOperador.getPreimpreso());
            operador.setUid(newOperador.getUid());
            return operadorRepository.save(operador);
        });
    }
}
