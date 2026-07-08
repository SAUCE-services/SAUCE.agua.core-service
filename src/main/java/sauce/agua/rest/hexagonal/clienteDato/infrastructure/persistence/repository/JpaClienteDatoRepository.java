package sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.entity.ClienteDatoEntity;

import java.util.Optional;

@Repository
public interface JpaClienteDatoRepository extends JpaRepository<ClienteDatoEntity, Long> {

    Optional<ClienteDatoEntity> findByClienteId(Long clienteId);

}
