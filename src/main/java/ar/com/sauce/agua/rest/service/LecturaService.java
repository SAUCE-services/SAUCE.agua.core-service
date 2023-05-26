/**
 * 
 */
package ar.com.sauce.agua.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.sauce.agua.rest.exception.LecturaNotFoundException;
import ar.com.sauce.agua.rest.model.Lectura;
import ar.com.sauce.agua.rest.repository.ILecturaRepository;

/**
 * @author daniel
 *
 */
@Service
public class LecturaService {

	@Autowired
	private ILecturaRepository repository;

	public Lectura findByMedidorId(String medidorId, Integer periodoId) {
		return repository.findByMedidorIdAndPeriodoId(medidorId, periodoId)
				.orElseThrow(() -> new LecturaNotFoundException(medidorId, periodoId));
	}

	public Lectura findPrevioByMedidorId(String medidorId, Integer periodoId) {
		return repository.findTopByMedidorIdAndPeriodoIdLessThanOrderByPeriodoIdDesc(medidorId, periodoId)
				.orElseThrow(() -> new LecturaNotFoundException(medidorId, periodoId));
	}

}
