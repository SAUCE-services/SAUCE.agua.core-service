package sauce.agua.rest.hexagonal.operador.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import sauce.agua.rest.hexagonal.operador.infrastructure.persistence.entity.OperadorEntity;

import java.util.Optional;

@Repository
public interface JpaOperadorRepository extends JpaRepository<OperadorEntity, Integer> {

    Optional<OperadorEntity> findByOperadorId(Integer operadorId);

    Optional<OperadorEntity> findTopByOrderByOperadorIdDesc();

    @Modifying
    void deleteByOperadorId(Integer operadorId);

}
