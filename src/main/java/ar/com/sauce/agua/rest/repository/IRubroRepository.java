/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.Rubro;
import ar.com.sauce.agua.rest.model.pk.RubroPk;

/**
 * @author daniel
 *
 */
@Repository
public interface IRubroRepository extends JpaRepository<Rubro, RubroPk> {

	public Optional<Rubro> findTopByRubroId(Integer rubroId);

}
