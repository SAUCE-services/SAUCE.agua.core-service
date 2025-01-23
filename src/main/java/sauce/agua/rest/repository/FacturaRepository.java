/**
 * 
 */
package sauce.agua.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sauce.agua.rest.model.Factura;
import sauce.agua.rest.model.pk.FacturaPk;

/**
 * @author daniel
 *
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, FacturaPk> {
	public List<Factura> findAllByPeriodoIdAndAnulada(Integer periodoId, Byte anulada);

	public List<Factura> findAllByPeriodoIdAndPagadaAndAnuladaAndCanceladaAndClienteIdIn(Integer periodoId, Byte pagada,
			Byte anulada, Byte cancelada, List<Long> clienteIds);

	public List<Factura> findAllByUniqueIdIn(List<Long> uniqueIds);

	public Optional<Factura> findByPrefijoIdAndFacturaId(Integer prefijoId, Long facturaId);

	public List<Factura> findTop6ByClienteIdAndPeriodoIdLessThanAndPagadaAndAnuladaAndCanceladaOrderByFacturaIdDesc(
			Long clienteId, Integer periodoIdReferencia, Byte pagada, Byte anulada, Byte cancelada);

}
