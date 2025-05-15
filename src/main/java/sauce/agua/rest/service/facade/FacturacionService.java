/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.Period;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.PeriodoException;
import sauce.agua.rest.exception.RubroException;
import sauce.agua.rest.model.Detalle;
import sauce.agua.rest.model.Factura;
import sauce.agua.rest.model.Novedad;
import sauce.agua.rest.model.Periodo;
import sauce.agua.rest.model.Rubro;
import sauce.agua.rest.repository.DetalleRepository;
import sauce.agua.rest.repository.FacturaRepository;
import sauce.agua.rest.repository.NovedadRepository;
import sauce.agua.rest.repository.PeriodoRepository;
import sauce.agua.rest.repository.RubroRepository;
import sauce.agua.rest.util.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class FacturacionService {

	private final RubroRepository rubrorepository;
	private final PeriodoRepository periodorepository;
	private final FacturaRepository facturarepository;
	private final DetalleRepository detallerepository;
	private final NovedadRepository novedadrepository;

	public FacturacionService(RubroRepository rubrorepository,
							  PeriodoRepository periodorepository,
							  FacturaRepository facturarepository,
							  DetalleRepository detallerepository,
							  NovedadRepository novedadrepository) {
		this.rubrorepository = rubrorepository;
		this.periodorepository = periodorepository;
		this.facturarepository = facturarepository;
		this.detallerepository = detallerepository;
		this.novedadrepository = novedadrepository;
	}

	@Transactional
	public void adjust(Integer prefijoId, Long facturaId, Integer decimals) {
		// Verifica la existencia del Rubro 70 y 71
		Rubro rubro = rubrorepository.findTopByRubroId(70).orElse(new Rubro());
		if (rubro.getRubroId() == null) {
			rubro.setRubroId(70);
			rubro.setFecha(OffsetDateTime.now());
			rubro.setConcepto("Ajuste redondeo");
			rubro.setPreciounitario(new BigDecimal(1));
			rubrorepository.save(rubro);
		}
		rubro = rubrorepository.findTopByRubroId(71).orElse(new Rubro());
		if (rubro.getRubroId() == null) {
			rubro.setRubroId(71);
			rubro.setFecha(OffsetDateTime.now());
			rubro.setConcepto("Ajuste redondeo período anterior");
			rubro.setPreciounitario(new BigDecimal(1));
			rubrorepository.save(rubro);
		}

		// Verificar si existe ajuste
		Detalle detalle = detallerepository.findByPrefijoIdAndFacturaIdAndRubroId(prefijoId, facturaId, 70)
				.orElse(new Detalle());
		if (detalle.getDetalleId() != null)
			return;

		// Factura
		Factura factura = facturarepository.findByPrefijoIdAndFacturaId(prefijoId, facturaId).orElse(new Factura());
		if (factura.getUniqueId() == null)
			return;

		// Redondea el importe total
		BigDecimal total_ajustado = Tool.rounding(factura.getTotal(), decimals);
		BigDecimal ajuste = total_ajustado.subtract(factura.getTotal());

		if (ajuste.compareTo(new BigDecimal(0)) == 0)
			return;

		var rubroId = 70;
		rubro = rubrorepository.findTopByRubroId(rubroId).orElseThrow(() -> new RubroException(rubroId));
		// Agregar Novedad, primero la elimina
		Novedad novedad = novedadrepository
				.findByClienteIdAndPeriodoIdAndRubroId(factura.getClienteId(), factura.getPeriodoId(), 70)
				.orElse(new Novedad());
		if (novedad.getNovedadId() != null)
			novedadrepository.delete(novedad);

		novedad = new Novedad();
		novedad.setClienteId(factura.getClienteId());
		novedad.setPeriodoId(factura.getPeriodoId());
		novedad.setRubroId(70);
		novedad.setFecha(OffsetDateTime.now());
		novedad.setCantidad(new BigDecimal(1));
		novedad.setImporte(ajuste);
		novedad.setVeces(1);
		novedadrepository.save(novedad);

		// Novedad del periodo siguiente, primero la elimina
		novedad = novedadrepository
				.findByClienteIdAndPeriodoIdAndRubroId(factura.getClienteId(), factura.getPeriodoId() + 1, 71)
				.orElse(new Novedad());
		if (novedad.getNovedadId() != null)
			novedadrepository.delete(novedad);

		novedad = new Novedad();
		novedad.setClienteId(factura.getClienteId());
		novedad.setPeriodoId(factura.getPeriodoId() + 1);
		novedad.setRubroId(71);
		novedad.setFecha(OffsetDateTime.now());
		novedad.setCantidad(new BigDecimal(1));
		novedad.setImporte(ajuste.multiply(new BigDecimal(-1)));
		novedad.setVeces(1);
		novedadrepository.save(novedad);

		// Agregar Detalle
		detalle = new Detalle();
		detalle.setPrefijoId(prefijoId);
		detalle.setFacturaId(facturaId);
		detalle.setRubroId(70);
		detalle.setConcepto(rubro.getConcepto());
		detalle.setCantidad(new BigDecimal(1));
		detalle.setPrecioUnitario(ajuste);
		detalle.setIva((byte) 0);
		detallerepository.save(detalle);

		factura.setTotal(total_ajustado);
		facturarepository.save(factura);
	}

	public String recalculateCodigoPagoFacil(Factura factura) {
		log.debug("Processing FacturacionService.recalculateCodigoPagoFacil");
		logFactura(factura);
		Periodo periodo = periodorepository.findByPeriodoId(factura.getPeriodoId()).orElseThrow(() -> new PeriodoException(factura.getPeriodoId()));
		logPeriodo(periodo);

		String string = "";
		string += "1972";
		log.debug("Institucion - {}", string);
		string += new DecimalFormat("00000000").format(factura.getTotal().multiply(new BigDecimal(100)));
		if (factura.getTotal().compareTo(new BigDecimal("999999.99")) > 0) {
			return "00";
		}
		log.debug("Total - {}", string);
		string += String.format("%02d", periodo.getFechaPrimero().getYear() - 2000);
		log.debug("Fecha - {}", string);
		string += String.format("%03d", 1 + periodo.getFechaPrimero().getDayOfYear());
		log.debug("Dia - {}", string);
		string += transactionIdFactura(factura);
		log.debug("Transaccion - {}", string);
		string += "0"; // Moneda
		log.debug("Moneda - {}", string);
		var interesCalculado = Tool.interes(factura.getTotal(), factura.getTasa(), periodo.getFechaPrimero(), periodo.getFechaSegundo());
		log.debug("Interes - {}", interesCalculado);
		var eliminarSegundoVencimiento = false;
		if (interesCalculado.compareTo(new BigDecimal("9999.99")) > 0) {
			interesCalculado = BigDecimal.ZERO;
			eliminarSegundoVencimiento = true;
			log.debug("Segundo vencimiento eliminado");
		}
		string += new DecimalFormat("000000").format(interesCalculado.multiply(new BigDecimal(100)));
		log.debug("Interes - {}", string);
		var days = Period.between(periodo.getFechaPrimero().toLocalDate(), periodo.getFechaSegundo().toLocalDate()).getDays();
		if (eliminarSegundoVencimiento) {
			days = 0;
		}
		string += String.format("%02d", days);
		log.debug("Dias - {}", string);
		string += digitoVerificador(string);
		log.debug("Digito Verificador - {}", string);
		string += digitoVerificador(string);
		log.debug("Digito Verificador - {}", string);

		log.debug("Codigo PagoFacil calculado: {}", string);
		return string;
	}

	private void logPeriodo(Periodo periodo) {
		try {
			log.debug("Periodo: {}", JsonMapper
					.builder()
					.findAndAddModules()
					.build()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(periodo));
		} catch (JsonProcessingException e) {
			log.debug("Periodo jsonify error: {}", e.getMessage());
		}
	}

	private void logFactura(Factura factura) {
		try {
			log.debug("Factura: {}", JsonMapper
					.builder()
					.findAndAddModules()
					.build()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(factura));
		} catch (JsonProcessingException e) {
			log.debug("Factura jsonify error: {}", e.getMessage());
		}
	}

	private String transactionIdFactura(Factura factura) {
		log.debug("Processing FacturacionService.transactionIdFactura");
		String string = "1";
		log.debug("Uno - {}", string);
		string += String.format("%05d", factura.getClienteId());
		log.debug("Cliente - {}", string);
		string += String.format("%08d", factura.getFacturaId());
		log.debug("Factura - {}", string);
		return string;
	}

	private Integer digitoVerificador(String string) {
		int acumulador = 0;
		String constante = "135793579357935793579357935793579357935793579";

		for (int ciclo = 0; ciclo < string.length(); ciclo++)
			acumulador += (string.charAt(ciclo) - 48) * (constante.charAt(ciclo) - 48);
		acumulador /= 2; // Division entera

		return acumulador % 10; // Resto de la division
	}

	public String i2of5(String string) {
		if ((string.length() & 1) == 1)
			return "";
		StringBuilder code = new StringBuilder();
		code.append((char) 33);
		for (int ciclo = 0; ciclo < string.length(); ciclo += 2) {
			int value = Integer.parseInt(string.substring(ciclo, ciclo + 2));
			int caracter = switch (value) {
                case 92 -> 196;
                case 93 -> 197;
                case 94 -> 199;
                case 95 -> 201;
                case 96 -> 209;
                case 97 -> 214;
                case 98 -> 220;
                case 99 -> 225;
                default -> value + 35;
            };
            code.append((char) caracter);
		}
		code.append((char) 34);
		log.debug("I2of5 -> {}", code);

		return code.toString();
	}
}
