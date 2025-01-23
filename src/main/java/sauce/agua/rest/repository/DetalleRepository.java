/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Detalle;
import sauce.agua.rest.model.pk.DetallePk;

/**
 * @author daniel
 *
 */
@Repository
public interface DetalleRepository extends JpaRepository<Detalle, DetallePk> {

	public List<Detalle> findAllByPrefijoIdAndFacturaIdOrderByRubroId(Integer prefijoId, Long facturaId);

	public Optional<Detalle> findByPrefijoIdAndFacturaIdAndRubroId(Integer prefijoId, Long facturaId, Integer rubroId);

}
