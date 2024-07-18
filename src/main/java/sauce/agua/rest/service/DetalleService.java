/**
 * 
 */
package sauce.agua.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sauce.agua.rest.model.Detalle;
import sauce.agua.rest.repository.IDetalleRepository;

/**
 * @author daniel
 *
 */
@Service
public class DetalleService {

	@Autowired
	private IDetalleRepository repository;

	public List<Detalle> findAllByFactura(Integer prefijoId, Long facturaId) {
		return repository.findAllByPrefijoIdAndFacturaIdOrderByRubroId(prefijoId, facturaId);
	}

}
