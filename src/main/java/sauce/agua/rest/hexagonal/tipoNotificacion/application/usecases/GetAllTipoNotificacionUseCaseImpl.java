package sauce.agua.rest.hexagonal.tipoNotificacion.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.in.GetAllTipoNotificacionUseCase;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.out.TipoNotificacionRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllTipoNotificacionUseCaseImpl implements GetAllTipoNotificacionUseCase {

    private final TipoNotificacionRepository tipoNotificacionRepository;

    @Override
    public List<TipoNotificacion> getAllTipoNotificacion() {
        return tipoNotificacionRepository.findAll();
    }
}
