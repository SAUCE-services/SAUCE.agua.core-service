/**
 * 
 */
package sauce.agua.rest.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.PeriodoNotFoundException;
import sauce.agua.rest.model.Periodo;
import sauce.agua.rest.model.view.PeriodoPago;
import sauce.agua.rest.repository.IPeriodoRepository;
import sauce.agua.rest.repository.view.IPeriodoPagoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class PeriodoService {
	@Autowired
	private IPeriodoRepository repository;

	@Autowired
	private IPeriodoPagoRepository periodopagorepository;

	public List<Periodo> findAll() {
		return repository.findAll(Sort.by("periodoId").descending());
	}

	public Periodo findByPeriodoId(Integer periodoId) {
		return repository.findByPeriodoId(periodoId).orElseThrow(() -> new PeriodoNotFoundException(periodoId));
	}

	public Periodo add(Periodo periodo) {
		log.debug("Add -> " + periodo.toString());
		periodo = repository.save(periodo);
		return periodo;
	}

	public Periodo update(Periodo newPeriodo, Integer periodoId) {
		return repository.findByPeriodoId(periodoId).map(periodo -> {
			periodo.setDescripcion(newPeriodo.getDescripcion());
			periodo.setFechaInicio(newPeriodo.getFechaInicio());
			periodo.setFechaFin(newPeriodo.getFechaFin());
			periodo.setFechaPrimero(newPeriodo.getFechaPrimero());
			periodo.setFechaSegundo(newPeriodo.getFechaSegundo());
			periodo.setTasa(newPeriodo.getTasa());
			periodo.setLeyenda(newPeriodo.getLeyenda());
			periodo.setLiquidado(newPeriodo.getLiquidado());
			periodo.setUid(newPeriodo.getUid());
			log.debug("Update -> " + periodo.toString());
			periodo = repository.save(periodo);
			return periodo;
		}).orElseThrow(() -> new PeriodoNotFoundException(periodoId));
	}

	public void delete(Integer periodoId) {
		repository.deleteByPeriodoId(periodoId);
	}

	public Periodo findLast() {
		return repository.findTopByOrderByPeriodoIdDesc().orElse(new Periodo());
	}

	public Periodo findByFecha(OffsetDateTime fecha) {
		log.debug("Service -> " + fecha.toString());
		Periodo periodo = repository
				.findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(fecha, fecha)
				.orElseThrow(() -> new PeriodoNotFoundException());
		return periodo;
	}

	public Periodo findToday() {
		OffsetDateTime today = OffsetDateTime.now(ZoneId.of("UTC")).minusHours(3);
		log.debug("Today -> " + today.toString());
		Periodo periodo = repository.findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(today)
				.orElseThrow(() -> new PeriodoNotFoundException());
		return periodo;
	}

	public List<Periodo> findAllRecaudadoByPeriodo(OffsetDateTime desde, OffsetDateTime hasta) {
		List<PeriodoPago> pagos = periodopagorepository.findAllByFechapagoBetween(desde, hasta,
				Sort.by("periodoId").ascending());
		Map<Integer, Periodo> periodos = new HashMap<Integer, Periodo>();
		for (PeriodoPago pago : pagos)
			if (!periodos.containsKey(pago.getPeriodoId())) {
				Periodo periodo = repository.findByPeriodoId(pago.getPeriodoId()).get();
				periodos.put(pago.getPeriodoId(), periodo);
			}
		return new ArrayList<Periodo>(periodos.values());
	}

}
