/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import sauce.agua.rest.exception.ClienteVolumenException;
import sauce.agua.rest.model.Factura;
import sauce.agua.rest.model.Periodo;
import sauce.agua.rest.service.ClienteVolumenService;
import sauce.agua.rest.service.FacturaService;
import sauce.agua.rest.service.MedidorService;
import sauce.agua.rest.service.PeriodoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DgeFileService {

	private final PeriodoService periodoService;
	private final FacturaService facturaService;
	private final Environment env;
    private final ConsumoService consumoService;
    private final MedidorService medidorService;

    public String generate(Integer periodoId, List<Long> clienteIds) throws IOException {
		Periodo periodo = periodoService.findByPeriodoId(periodoId);
		log.debug("Periodo -> {}", periodo.jsonify());
		OffsetDateTime fecha = periodo.getFechaInicio().plusHours(3);
		int anho = fecha.getYear();
		Integer mes = fecha.getMonthValue();

		String path = env.getProperty("path.files");

		String filename = path + "UVSPES." + anho + String.format("%02d", mes) + ".txt";

		List<Factura> facturas = facturaService.findAllDeudaByPeriodoIdAndClienteIds(periodoId, clienteIds);
		BigDecimal total = BigDecimal.ZERO;
		for (Factura factura : facturas) {
			total = total.add(factura.getTotal().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP));
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		// Registro Cabecera
		String line = String.format("%010d", facturas.size());
		log.debug("Line -> {}", line);
		line += new DecimalFormat("00000000000000").format(total);
		log.debug("Line -> {}", line);
		line += String.format("%1$" + 60 + "s", "");
		log.debug("Line -> {}", line);
		out.write(line);
		out.write("\n");
		for (Factura factura : facturas) {
			log.debug("Factura -> {}", factura.jsonify());
            var consumo = 0L;
            try {
                var medidor = medidorService.findColocadoByClienteId(factura.getClienteId());
                consumo = consumoService.calculateConsumo(factura.getClienteId(), factura.getPeriodoId(), medidor.getMedidorId(), periodo.getFechaFin()).getConsumo();
                log.debug("Consumo detectado -> {}", consumo);
            } catch (ClienteVolumenException e) {
                log.debug("Sin consumo");
            }
			line = "1";
			log.debug("Line -> {}", line);
			line += "35645.";
			log.debug("Line -> {}", line);
			line += String.format("%1$-" + 12 + "s", factura.getClienteId());
			log.debug("Line -> {}", line);
			line += String.format("%1$-" + 13 + "s", factura.getFacturaId());
			log.debug("Line -> {}", line);
			line += DateTimeFormatter.ofPattern("yyyyMMdd").format(periodo.getFechaPrimero().plusHours(3));
			log.debug("Line -> {}", line);
			line += DateTimeFormatter.ofPattern("yyyyMM").format(periodo.getFechaInicio().plusHours(3));
			log.debug("Line -> {}", line);
			line += new DecimalFormat("00000000000000")
					.format(factura.getTotal().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP));
			log.debug("Line -> {}", line);
			line += "00000000000000";
			log.debug("Line -> {}", line);
			line += "0000000000";
			log.debug("Line -> {}", line);
            line += new DecimalFormat("000").format(consumo);
            log.debug("Line -> {}", line);
			out.write(line);
			out.write("\n");
		}
		out.close();
		return filename;
	}

}
