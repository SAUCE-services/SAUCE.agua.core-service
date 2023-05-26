/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.Lectura;

/**
 * @author daniel
 *
 */
@Repository
public interface ILecturaRepository extends JpaRepository<Lectura, Long> {

	public Optional<Lectura> findByMedidorIdAndPeriodoId(String medidorId, Integer periodoId);

	public Optional<Lectura> findTopByMedidorIdAndPeriodoIdLessThanOrderByPeriodoIdDesc(String medidorId,
			Integer periodoId);

}
