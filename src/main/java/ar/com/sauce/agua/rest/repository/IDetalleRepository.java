/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.Detalle;
import ar.com.sauce.agua.rest.model.pk.DetallePk;

/**
 * @author daniel
 *
 */
@Repository
public interface IDetalleRepository extends JpaRepository<Detalle, DetallePk> {

	public List<Detalle> findAllByPrefijoIdAndFacturaIdOrderByRubroId(Integer prefijoId, Long facturaId);

	public Optional<Detalle> findByPrefijoIdAndFacturaIdAndRubroId(Integer prefijoId, Long facturaId, Integer rubroId);

}