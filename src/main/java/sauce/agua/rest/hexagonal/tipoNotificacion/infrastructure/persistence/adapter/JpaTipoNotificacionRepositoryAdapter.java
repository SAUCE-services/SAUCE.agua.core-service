package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.model.TipoNotificacion;
import sauce.agua.rest.hexagonal.tipoNotificacion.domain.ports.out.TipoNotificacionRepository;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.entity.TipoNotificacionEntity;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.mapper.TipoNotificacionMapper;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.repository.JpaTipoNotificacionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTipoNotificacionRepositoryAdapter implements TipoNotificacionRepository {

    private final JpaTipoNotificacionRepository jpaTipoNotificacionRepository;
    private final TipoNotificacionMapper tipoNotificacionMapper;

    @Override
    public List<TipoNotificacion> findAll() {
        return jpaTipoNotificacionRepository.findAll(Sort.by("tiponotificacionId").ascending()).stream()
                .map(tipoNotificacionMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TipoNotificacion> findByTiponotificacionId(Integer tiponotificacionId) {
        return jpaTipoNotificacionRepository.findByTiponotificacionId(tiponotificacionId)
                .map(tipoNotificacionMapper::toDomainModel);
    }
}
