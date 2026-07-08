package sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.entity.MedidorEntity;

import java.util.Optional;

@Repository
public interface JpaMedidorRepository extends JpaRepository<MedidorEntity, Long> {
    Optional<MedidorEntity> findTopByClienteId(Long clienteId, Sort sort);
    Optional<MedidorEntity> findTopByClienteIdAndFechaRetiroIsNull(Long clienteId, Sort sort);
}
