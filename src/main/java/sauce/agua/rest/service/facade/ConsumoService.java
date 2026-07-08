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
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.model.Desconexion;
import sauce.agua.rest.model.Lectura;
import sauce.agua.rest.model.Medicion;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.entity.PeriodoEntity;
import sauce.agua.rest.model.internal.DatoConsumo;
import sauce.agua.rest.service.DesconexionService;
import sauce.agua.rest.service.LecturaService;
import sauce.agua.rest.service.MedicionService;
import sauce.agua.rest.hexagonal.medidor.application.service.MedidorService;
import sauce.agua.rest.hexagonal.periodo.application.service.PeriodoService;
import sauce.agua.rest.util.Jsonifier;

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
		log.debug("DatoConsumo: {}", Jsonifier.builder(datoConsumo).build());

		boolean desconectado = false;

		Periodo periodo = periodoService.findByPeriodoId(periodoId);
		log.debug("Calculando consumo (1)");
		log.debug("Periodo: {}", Jsonifier.builder(periodo).build());

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
		log.debug("Medidor: {}", Jsonifier.builder(medidor).build());

		Lectura lectura;

		try {
			lectura = lecturaService.findByMedidorId(medidorId, periodoId);
		} catch (LecturaException e) {
			lectura = new Lectura();
		}
		log.debug("Calculando consumo (3)");
		log.debug("Lectura: {}", Jsonifier.builder(lectura).build());

		datoConsumo.setFechaActual(lectura.getFechaLectura());
		datoConsumo.setEstadoActual(medidor.getEstadoInicio());
		log.debug("Estado actual");
		log.debug("Dato consumo: {}", Jsonifier.builder(datoConsumo).build());

		if (lectura.getUniqueId() != null) {
			if (!medidor.getFechaColocacion().isAfter(periodo.getFechaInicio())) {
				datoConsumo.setEstadoActual(lectura.getEstado());
				var medicion = createOrUpdateMedicion(clienteId, lectura.getPeriodoId(), medidorId,
					lectura.getFechaLectura(), lectura.getEstado());
				log.debug("Calculando consumo (4)");
				log.debug("Medicion: {}", Jsonifier.builder(medicion).build());
			}
		} else {
			var medicion = createOrUpdateMedicion(clienteId, periodoId, medidorId,
				OffsetDateTime.now().minusDays(365), 0L);
			log.debug("Calculando consumo (5)");
			log.debug("Medicion: {}", Jsonifier.builder(medicion).build());
		}

        try {
			lectura = lecturaService.findPrevioByMedidorId(medidorId, periodoId);
		} catch (LecturaException e) {
			lectura = new Lectura();
		}
		log.debug("Lectura actual: {}", Jsonifier.builder(lectura).build());

		datoConsumo.setFechaAnterior(lectura.getFechaLectura());
		datoConsumo.setEstadoAnterior(medidor.getEstadoInicio());
		log.debug("Estado anterior");
		log.debug("Dato consumo: {}", Jsonifier.builder(datoConsumo).build());

		if (lectura.getUniqueId() != null) {
			if (!medidor.getFechaColocacion().isAfter(periodo.getFechaFin())) {
				datoConsumo.setEstadoAnterior(lectura.getEstado());
				var medicion = createOrUpdateMedicion(clienteId, lectura.getPeriodoId(), medidorId,
					lectura.getFechaLectura(), lectura.getEstado());
				log.debug("Calculando consumo (6)");
				log.debug("Medicion: {}", Jsonifier.builder(medicion).build());
			}
		} else {
			var medicion = createOrUpdateMedicion(clienteId, periodoId - 1, medidorId,
				OffsetDateTime.now().minusDays(365), 0L);
			log.debug("Calculando consumo (7)");
			log.debug("Medicion: {}", Jsonifier.builder(medicion).build());
		}

		datoConsumo.setConsumo(datoConsumo.getEstadoActual() - datoConsumo.getEstadoAnterior());
		log.debug("Consumo calculado");
		log.debug("Dato consumo: {}", Jsonifier.builder(datoConsumo).build());

		return datoConsumo;
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
		log.debug("Medicion calculado: {}", Jsonifier.builder(medicion).build());
		return medicion;
	}

}
