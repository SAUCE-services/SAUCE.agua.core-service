/**
 * 
 */
package sauce.agua.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import sauce.agua.rest.model.Detalle;
import sauce.agua.rest.repository.DetalleRepository;

/**
 * @author daniel
 *
 */
@Service
public class DetalleService {

	private final DetalleRepository repository;

	public DetalleService(DetalleRepository repository) {
		this.repository = repository;
	}

	public List<Detalle> findAllByFactura(Integer prefijoId, Long facturaId) {
		return repository.findAllByPrefijoIdAndFacturaIdOrderByRubroId(prefijoId, facturaId);
	}

}
