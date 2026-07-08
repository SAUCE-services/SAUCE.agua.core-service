package sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.entity.PosicionIvaEntity;

import java.util.Optional;

@Repository
public interface JpaPosicionIvaRepository extends JpaRepository<PosicionIvaEntity, Integer> {

    Optional<PosicionIvaEntity> findByPosicionId(Integer posicionId);

    @Modifying
    void deleteByPosicionId(Integer posicionId);

}
