/**
 * 
 */
package sauce.agua.rest.service.view;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.model.view.FacturaPmc;
import sauce.agua.rest.repository.view.IFacturaPmcRepository;

/**
 * @author daniel
 *
 */
@Service
public class FacturaPmcService {
	@Autowired
	private IFacturaPmcRepository repository;

	public List<FacturaPmc> findAllByPeriodo(OffsetDateTime desde, OffsetDateTime hasta) {
		return repository.findAllByFechaprimeroBetween(desde, hasta);
	}
	
}
