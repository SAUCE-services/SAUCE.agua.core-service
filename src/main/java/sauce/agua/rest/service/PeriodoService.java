/**
 * 
 */
package sauce.agua.rest.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.PeriodoException;
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

	private final IPeriodoRepository repository;
	private final IPeriodoPagoRepository periodopagorepository;

	public PeriodoService(IPeriodoRepository repository, IPeriodoPagoRepository periodopagorepository) {
		this.repository = repository;
		this.periodopagorepository = periodopagorepository;
	}

	public List<Periodo> findAll() {
		return repository.findAll(Sort.by("periodoId").descending());
	}

	public Periodo findByPeriodoId(Integer periodoId) {
		Periodo periodo = repository.findByPeriodoId(periodoId).orElseThrow(() -> new PeriodoException(periodoId));
		logPeriodo(periodo);
		return periodo;
	}

	public Periodo add(Periodo periodo) {
		log.debug("Processing add");
		logPeriodo(periodo);
		periodo = repository.save(periodo);
		return periodo;
	}

	public Periodo update(Periodo newPeriodo, Integer periodoId) {
		log.debug("Processing update");
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
			periodo = repository.save(periodo);
			logPeriodo(periodo);
			return periodo;
		}).orElseThrow(() -> new PeriodoException(periodoId));
	}

	@Transactional
	public void delete(Integer periodoId) {
		log.debug("Processing delete");
		repository.deleteByPeriodoId(periodoId);
	}

	public Periodo findLast() {
		log.debug("Processing findLast");
		Periodo periodo = repository.findTopByOrderByPeriodoIdDesc().orElse(new Periodo());
		logPeriodo(periodo);
		return periodo;
	}

	public Periodo findByFecha(OffsetDateTime fecha) {
		log.debug("Processing findByFecha -> {}", fecha);
		Periodo periodo = repository
				.findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(fecha, fecha)
				.orElseThrow(PeriodoException::new);
		logPeriodo(periodo);
		return periodo;
	}

	public Periodo findToday() {
		log.debug("Processing findToday");
		OffsetDateTime today = OffsetDateTime.now(ZoneId.of("UTC")).minusHours(3);
		log.debug("Today -> {}", today);
		Periodo periodo = repository.findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(today)
				.orElseThrow(PeriodoException::new);
		logPeriodo(periodo);
		return periodo;
	}

	public List<Periodo> findAllRecaudadoByPeriodo(OffsetDateTime desde, OffsetDateTime hasta) {
		log.debug("Processing findAllRecaudadoByPeriodo desde: {} hasta: {}", desde, hasta);
		
		List<PeriodoPago> pagos = periodopagorepository.findAllByFechapagoBetween(
				desde, 
				hasta,
				Sort.by("periodoId").ascending()
		);
		
		return pagos.stream()
				.map(PeriodoPago::getPeriodoId)
				.distinct()
				.map(periodoId -> repository.findByPeriodoId(periodoId)
						.orElseThrow(() -> new PeriodoException(periodoId)))
				.peek(this::logPeriodo)
				.collect(Collectors.toList());
	}

	private void logPeriodo(Periodo periodo) {
		try {
			log.debug("Periodo -> {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(periodo));
		} catch (JsonProcessingException e) {
			log.debug("Periodo jsonify error -> {}", e.getMessage());
		}

	}

}
