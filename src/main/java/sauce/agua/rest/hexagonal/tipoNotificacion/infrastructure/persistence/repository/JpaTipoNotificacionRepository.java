package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.entity.TipoNotificacionEntity;

import java.util.Optional;

@Repository
public interface JpaTipoNotificacionRepository extends JpaRepository<TipoNotificacionEntity, Integer> {
    Optional<TipoNotificacionEntity> findByTiponotificacionId(Integer tiponotificacionId);
}
