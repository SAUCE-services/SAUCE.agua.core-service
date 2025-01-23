/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.Period;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	@Autowired
	private RubroRepository rubrorepository;

	@Autowired
	private PeriodoRepository periodorepository;

	@Autowired
	private FacturaRepository facturarepository;

	@Autowired
	private DetalleRepository detallerepository;

	@Autowired
	private NovedadRepository novedadrepository;

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

		rubro = rubrorepository.findTopByRubroId(70).get();
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
		log.debug("Factura -> {}", factura.toString());
		Periodo periodo = periodorepository.findByPeriodoId(factura.getPeriodoId()).get();
		log.debug("Periodo: {}", periodo.toString());

		String string = "";
		string += "1972";
		string += new DecimalFormat("00000000").format(factura.getTotal().multiply(new BigDecimal(100)));
		string += String.format("%02d", periodo.getFechaPrimero().getYear() - 2000);
		string += String.format("%03d", 1 + periodo.getFechaPrimero().getDayOfYear());
		string += transactionIdFactura(factura);
		string += "0"; // Moneda
		string += new DecimalFormat("000000").format(Tool
				.interes(factura.getTotal(), factura.getTasa(), periodo.getFechaPrimero(), periodo.getFechaSegundo())
				.multiply(new BigDecimal(100)));
		string += String.format("%02d", Period
				.between(periodo.getFechaPrimero().toLocalDate(), periodo.getFechaSegundo().toLocalDate()).getDays());
		string += digitoVerificador(string);
		string += digitoVerificador(string);

		log.debug("Factura: {} -> calculado: {}", factura.toString(), string);
		return string;
	}

	private String transactionIdFactura(Factura factura) {
		String string = "1";
		string += String.format("%05d", factura.getClienteId());
		string += String.format("%08d", factura.getFacturaId());
		return string;
	}

	private Integer digitoVerificador(String string) {
		Integer acumulador = 0;
		String constante = "135793579357935793579357935793579357935793579";

		for (Integer ciclo = 0; ciclo < string.length(); ciclo++)
			acumulador += (string.charAt(ciclo) - 48) * (constante.charAt(ciclo) - 48);
		acumulador /= 2; // Division entera

		return acumulador % 10; // Resto de la division
	}

	public String i2of5(String string) {
		if ((string.length() & 1) == 1)
			return "";
		String code = "";
		code += (char) 33;
		for (Integer ciclo = 0; ciclo < string.length(); ciclo += 2) {
			Integer value = Integer.parseInt(string.substring(ciclo, ciclo + 2));
			int caracter = 0;
			switch (value) {
			case 92:
				caracter = 196;
				break;
			case 93:
				caracter = 197;
				break;
			case 94:
				caracter = 199;
				break;
			case 95:
				caracter = 201;
				break;
			case 96:
				caracter = 209;
				break;
			case 97:
				caracter = 214;
				break;
			case 98:
				caracter = 220;
				break;
			case 99:
				caracter = 225;
				break;
			default:
				caracter = value + 35;
				break;
			}
			code += (char) caracter;
		}
		code += (char) 34;
		log.debug("I2of5 -> {}", code);

		return code;
	}
}
