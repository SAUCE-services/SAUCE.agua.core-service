/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Novedad;
import sauce.agua.rest.model.pk.NovedadPk;

/**
 * @author daniel
 *
 */
@Repository
public interface NovedadRepository extends JpaRepository<Novedad, NovedadPk> {

	public Optional<Novedad> findByClienteIdAndPeriodoIdAndRubroId(Long clienteId, Integer periodoId, Integer rubroId);

}
