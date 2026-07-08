package sauce.agua.rest.hexagonal.tipoNotificacion.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.in.GetTipoNotificacionByIdUseCase;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.out.TipoNotificacionRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetTipoNotificacionByIdUseCaseImpl implements GetTipoNotificacionByIdUseCase {

    private final TipoNotificacionRepository tipoNotificacionRepository;

    @Override
    public Optional<TipoNotificacion> getTipoNotificacionById(Integer tiponotificacionId) {
        return tipoNotificacionRepository.findByTiponotificacionId(tiponotificacionId);
    }
}
