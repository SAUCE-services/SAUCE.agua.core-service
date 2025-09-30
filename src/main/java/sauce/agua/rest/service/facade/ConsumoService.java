/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.DesconexionException;
import sauce.agua.rest.exception.LecturaException;
import sauce.agua.rest.exception.MedicionException;
import sauce.agua.rest.model.Desconexion;
import sauce.agua.rest.model.Lectura;
import sauce.agua.rest.model.Medicion;
import sauce.agua.rest.model.Medidor;
import sauce.agua.rest.model.Periodo;
import sauce.agua.rest.model.internal.DatoConsumo;
import sauce.agua.rest.service.DesconexionService;
import sauce.agua.rest.service.LecturaService;
import sauce.agua.rest.service.MedicionService;
import sauce.agua.rest.service.MedidorService;
import sauce.agua.rest.service.PeriodoService;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumoService {

	private final LecturaService lecturaService;
	private final MedidorService medidorService;
	private final PeriodoService periodoService;
	private final MedicionService medicionService;
	private final DesconexionService desconexionService;

	public DatoConsumo calculateConsumo(Long clienteId, Integer periodoId, String medidorId,
			OffsetDateTime fechaEmision) {
		log.debug("Calculando consumo para cliente {} periodo {} medidor {} fechaEmision {}", clienteId, periodoId, medidorId, fechaEmision);
		DatoConsumo datoConsumo = new DatoConsumo(OffsetDateTime.of(1980, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
				OffsetDateTime.of(1980, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC), 0L, 0L, 0L);
		logConsumo(datoConsumo);

		boolean desconectado = false;

		Periodo periodo = periodoService.findByPeriodoId(periodoId);
		log.debug("Calculando consumo (1)");
		logPeriodo(periodo);

		Desconexion desconexion;

		try {
			desconexion = desconexionService.findByClienteId(clienteId, fechaEmision);
		} catch (DesconexionException e) {
			desconexion = new Desconexion();
		}

		if (desconexion.getUniqueId() != null) {
			if (desconexion.getFechaReconexion() == null) {
				desconectado = true;
			}
            assert desconexion.getFechaReconexion() != null;
            if (desconexion.getFechaReconexion().isAfter(periodo.getFechaFin())) {
				if (!desconexion.getFechaDesconexion().isAfter(periodo.getFechaInicio())) {
					desconectado = true;
				}
			}
		}

		if (desconectado) {
			return new DatoConsumo();
		}

		Medidor medidor = medidorService.findByClienteId(clienteId, true);
		log.debug("Calculando consumo (2)");
		logMedidor(medidor);

		Lectura lectura;

		try {
			lectura = lecturaService.findByMedidorId(medidorId, periodoId);
		} catch (LecturaException e) {
			lectura = new Lectura();
		}
		log.debug("Calculando consumo (3)");
		logLectura(lectura);

		datoConsumo.setFechaActual(lectura.getFechaLectura());
		datoConsumo.setEstadoActual(medidor.getEstadoInicio());
		log.debug("Estado actual");
		logConsumo(datoConsumo);

		if (lectura.getUniqueId() != null) {
			if (!medidor.getFechaColocacion().isAfter(periodo.getFechaInicio())) {
				datoConsumo.setEstadoActual(lectura.getEstado());
				var medicion = createOrUpdateMedicion(clienteId, lectura.getPeriodoId(), medidorId,
					lectura.getFechaLectura(), lectura.getEstado());
				log.debug("Calculando consumo (4)");
				logMedicion(medicion);
			}
		} else {
			var medicion = createOrUpdateMedicion(clienteId, periodoId, medidorId,
				OffsetDateTime.now().minusDays(365), 0L);
			log.debug("Calculando consumo (5)");
			logMedicion(medicion);
		}

        try {
			lectura = lecturaService.findPrevioByMedidorId(medidorId, periodoId);
		} catch (LecturaException e) {
			lectura = new Lectura();
		}
		logLectura(lectura);

		datoConsumo.setFechaAnterior(lectura.getFechaLectura());
		datoConsumo.setEstadoAnterior(medidor.getEstadoInicio());
		log.debug("Estado anterior");
		logConsumo(datoConsumo);

		if (lectura.getUniqueId() != null) {
			if (!medidor.getFechaColocacion().isAfter(periodo.getFechaFin())) {
				datoConsumo.setEstadoAnterior(lectura.getEstado());
				var medicion = createOrUpdateMedicion(clienteId, lectura.getPeriodoId(), medidorId,
					lectura.getFechaLectura(), lectura.getEstado());
				log.debug("Calculando consumo (6)");
				logMedicion(medicion);
			}
		} else {
			var medicion = createOrUpdateMedicion(clienteId, periodoId - 1, medidorId,
				OffsetDateTime.now().minusDays(365), 0L);
			log.debug("Calculando consumo (7)");
			logMedicion(medicion);
		}

		datoConsumo.setConsumo(datoConsumo.getEstadoActual() - datoConsumo.getEstadoAnterior());
		log.debug("Consumo calculado");
		logConsumo(datoConsumo);

		return datoConsumo;
	}

	private void logConsumo(DatoConsumo datoConsumo) {
		try {
			log.debug("DatoConsumo: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(datoConsumo));
		} catch (JsonProcessingException e) {
			log.debug("DatoConsumo jsonify error: {}", e.getMessage());
		}
	}

	private void logMedicion(Medicion medicion) {
		try {
			log.debug("Medicion: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(medicion));
		} catch (JsonProcessingException e) {
			log.debug("Medicion jsonify error: {}", e.getMessage());
		}
	}

	private void logLectura(Lectura lectura) {
		try {
			log.debug("Lectura: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(lectura));
		} catch (JsonProcessingException e) {
			log.debug("Lectura jsonify error: {}", e.getMessage());
		}
	}

	private void logPeriodo(Periodo periodo) {
		try {
			log.debug("Periodo: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(periodo));
		} catch (JsonProcessingException e) {
			log.debug("Periodo jsonify error: {}", e.getMessage());
		}
	}

	private void logMedidor(Medidor medidor) {
		try {
			log.debug("Medidor: {}", JsonMapper.builder().findAndAddModules().build().writerWithDefaultPrettyPrinter().writeValueAsString(medidor));
		} catch (JsonProcessingException e) {
			log.debug("Medidor jsonify error: {}", e.getMessage());
		}
	}

	private Medicion createOrUpdateMedicion(Long clienteId, Integer periodoId, String medidorId, 
			OffsetDateTime fechaLectura, Long estado) {
		Medicion medicion = null;
		try {
			medicion = medicionService.findByClienteId(clienteId, periodoId);
		} catch (MedicionException e) {
			medicion = new Medicion();
		}

		medicion.setClienteId(clienteId);
		medicion.setPeriodoId(periodoId);
		medicion.setMedidorId(medidorId);
		medicion.setFechaLectura(fechaLectura);
		medicion.setEstado(estado);

		if (medicion.getUniqueId() == null) {
			medicion = medicionService.add(medicion);
		} else {
			medicion = medicionService.update(medicion, medicion.getUniqueId());
		}
		logMedicion(medicion);
		return medicion;
	}

}
