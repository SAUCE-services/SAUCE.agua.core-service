/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ConsumoService {

	@Autowired
	private LecturaService lecturaService;

	@Autowired
	private MedidorService medidorService;

	@Autowired
	private PeriodoService periodoService;

	@Autowired
	private MedicionService medicionService;

	@Autowired
	private DesconexionService desconexionService;

	public DatoConsumo calculateConsumo(Long clienteId, Integer periodoId, String medidorId,
			OffsetDateTime fechaEmision) {
		DatoConsumo datoConsumo = new DatoConsumo(OffsetDateTime.of(1980, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
				OffsetDateTime.of(1980, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC), 0L, 0L, 0L);

		Boolean desconectado = false;

		Periodo periodo = periodoService.findByPeriodoId(periodoId);
		Desconexion desconexion = null;

		try {
			desconexion = desconexionService.findByClienteId(clienteId, fechaEmision);
		} catch (DesconexionException e) {
			desconexion = new Desconexion();
		}

		if (desconexion.getUniqueId() != null) {
			if (desconexion.getFechaReconexion() == null) {
				desconectado = true;
			}
			if (desconexion.getFechaReconexion().compareTo(periodo.getFechaFin()) > 0) {
				if (desconexion.getFechaDesconexion().compareTo(periodo.getFechaInicio()) <= 0) {
					desconectado = true;
				}
			}
		}

		if (desconectado) {
			return new DatoConsumo();
		}

		Medidor medidor = medidorService.findByClienteId(clienteId, true);

		Lectura lectura = null;

		try {
			lectura = lecturaService.findByMedidorId(medidorId, periodoId);
		} catch (LecturaException e) {
			lectura = new Lectura();
		}

		datoConsumo.setFechaActual(lectura.getFechaLectura());
		datoConsumo.setEstadoActual(medidor.getEstadoInicio());

		if (lectura.getUniqueId() != null) {
			if (medidor.getFechaColocacion().compareTo(periodo.getFechaInicio()) <= 0) {
				datoConsumo.setEstadoActual(lectura.getEstado());

				Medicion medicion = null;
				try {
					medicion = medicionService.findByClienteId(clienteId, lectura.getPeriodoId());
				} catch (MedicionException e) {
					medicion = new Medicion();
				}

				medicion.setClienteId(clienteId);
				medicion.setPeriodoId(lectura.getPeriodoId());
				medicion.setMedidorId(medidorId);
				medicion.setFechaLectura(lectura.getFechaLectura());
				medicion.setEstado(lectura.getEstado());

				if (medicion.getUniqueId() == null) {
					medicion = medicionService.add(medicion);
				} else {
					medicion = medicionService.update(medicion, medicion.getUniqueId());
				}

			}
		} else {
			Medicion medicion = null;
			try {
				medicion = medicionService.findByClienteId(clienteId, periodoId);
			} catch (MedicionException e) {
				medicion = new Medicion();
			}

			medicion.setClienteId(clienteId);
			medicion.setPeriodoId(periodoId);
			medicion.setMedidorId(medidorId);
			medicion.setFechaLectura(OffsetDateTime.now().minusDays(365));
			medicion.setEstado(0L);

			if (medicion.getUniqueId() == null) {
				medicion = medicionService.add(medicion);
			} else {
				medicion = medicionService.update(medicion, medicion.getUniqueId());
			}
		}

		lectura = null;

		try {
			lectura = lecturaService.findPrevioByMedidorId(medidorId, periodoId);
		} catch (LecturaException e) {
			lectura = new Lectura();
		}

		datoConsumo.setFechaAnterior(lectura.getFechaLectura());
		datoConsumo.setEstadoAnterior(medidor.getEstadoInicio());

		if (lectura.getUniqueId() != null) {
			if (medidor.getFechaColocacion().compareTo(periodo.getFechaFin()) <= 0) {
				datoConsumo.setEstadoAnterior(lectura.getEstado());

				Medicion medicion = null;
				try {
					medicion = medicionService.findByClienteId(clienteId, lectura.getPeriodoId());
				} catch (MedicionException e) {
					medicion = new Medicion();
				}

				medicion.setClienteId(clienteId);
				medicion.setPeriodoId(lectura.getPeriodoId());
				medicion.setMedidorId(medidorId);
				medicion.setFechaLectura(lectura.getFechaLectura());
				medicion.setEstado(lectura.getEstado());

				if (medicion.getUniqueId() == null) {
					medicion = medicionService.add(medicion);
				} else {
					medicion = medicionService.update(medicion, medicion.getUniqueId());
				}

			}
		} else {
			Medicion medicion = null;
			try {
				medicion = medicionService.findByClienteId(clienteId, periodoId - 1);
			} catch (MedicionException e) {
				medicion = new Medicion();
			}

			medicion.setClienteId(clienteId);
			medicion.setPeriodoId(periodoId - 1);
			medicion.setMedidorId(medidorId);
			medicion.setFechaLectura(OffsetDateTime.now().minusDays(365));
			medicion.setEstado(0L);

			if (medicion.getUniqueId() == null) {
				medicion = medicionService.add(medicion);
			} else {
				medicion = medicionService.update(medicion, medicion.getUniqueId());
			}
		}

		datoConsumo.setConsumo(datoConsumo.getEstadoActual() - datoConsumo.getEstadoAnterior());

		return datoConsumo;
	}

}
