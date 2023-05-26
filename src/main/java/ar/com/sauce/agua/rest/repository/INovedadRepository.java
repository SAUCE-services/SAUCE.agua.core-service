/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.Novedad;
import ar.com.sauce.agua.rest.model.pk.NovedadPk;

/**
 * @author daniel
 *
 */
@Repository
public interface INovedadRepository extends JpaRepository<Novedad, NovedadPk> {

	public Optional<Novedad> findByClienteIdAndPeriodoIdAndRubroId(Long clienteId, Integer periodoId, Integer rubroId);

}
