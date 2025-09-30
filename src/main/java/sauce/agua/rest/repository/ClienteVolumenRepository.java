package sauce.agua.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sauce.agua.rest.model.ClienteVolumen;

import java.util.Optional;

public interface ClienteVolumenRepository extends JpaRepository<ClienteVolumen, Long> {

    Optional<ClienteVolumen> findByClienteIdAndPeriodoId(Long clienteId, Integer periodoId);

}
