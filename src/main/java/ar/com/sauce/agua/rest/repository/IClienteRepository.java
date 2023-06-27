/**
 * 
 */
package ar.com.sauce.agua.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.sauce.agua.rest.model.Cliente;

/**
 * @author daniel
 *
 */
@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	public List<Cliente> findAllByClienteId(Long clienteId, Sort sort);

	public List<Cliente> findAllByFechaBajaIsNull(Sort sort);

	public List<Cliente> findAllByFechaBajaIsNullAndZonaAndRuta(Integer zona, Integer ruta, Sort sort);

	public List<Cliente> findAllByFechaBajaIsNullAndCobroLessThan(Integer cobro, Sort sort);

	public List<Cliente> findAllByFechaBajaIsNullAndZona(Integer zona, Sort sort);

	public List<Cliente> findAllByFechaBajaIsNullAndClienteIdBetween(Long clienteIdDesde, Long clienteIdHasta,
			Sort sort);

	public List<Cliente> findAllByFechaBajaIsNullAndCobro(Integer cobro, Sort sort);

	public List<Cliente> findTop50ByFechaBajaIsNullAndApellidoLike(String string, Sort sort);

	public Optional<Cliente> findTopByClienteIdOrderByFechaAltaDesc(Long clienteId);

	public Optional<Cliente> findTopByOrderByClienteIdDescFechaAltaDesc();

	public Optional<Cliente> findByUniqueId(Long uniqueId);

}
