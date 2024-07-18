/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sauce.agua.rest.misc.PagoMisCuentasDetalle;
import sauce.agua.rest.misc.PagoMisCuentasHeader;
import sauce.agua.rest.misc.PagoMisCuentasTrailer;
import sauce.agua.rest.model.view.FacturaPmc;
import sauce.agua.rest.repository.view.IFacturaPmcRepository;
import sauce.agua.rest.util.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class PagoMisCuentasService {
	@Autowired
	private IFacturaPmcRepository repository;

	public String generate(OffsetDateTime desde, OffsetDateTime hasta) throws IOException {
		Integer cantidadregistros = 0;
		BigDecimal importetotal = new BigDecimal(0);

		String filename = "pago_mis_cuentas";

		File file = new File(filename);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		PagoMisCuentasHeader header = new PagoMisCuentasHeader(0, 400, 462, Tool.hourAbsoluteArgentina(), "");
		writer.write(header.getBarra());
		writer.write("\r\n");
		for (FacturaPmc facturapmc : repository.findAllByFechaprimeroBetween(desde, hasta)) {
			PagoMisCuentasDetalle detalle = new PagoMisCuentasDetalle();
			detalle.setCodigoregistro(5);
			detalle.setNroreferencia("1" + String.format("%05d", facturapmc.getClienteId()));
			detalle.setIdfactura(detalle.getNroreferencia() + String.format("%02d", facturapmc.getPrefijoId())
					+ String.format("%010d", facturapmc.getFacturaId()));
			detalle.setCodigomoneda(0);
			detalle.setFecha1ervencimiento(facturapmc.getFechaprimero());
			detalle.setImporte1ervencimiento(facturapmc.getTotal());
			detalle.setFecha2dovencimiento(facturapmc.getFechasegundo());
			detalle.setImporte2dovencimiento(facturapmc.getTotal().add(Tool.interes(facturapmc.getTotal(), facturapmc.getTasa(),
					facturapmc.getFechaprimero(), facturapmc.getFechasegundo())));
			detalle.setFecha3ervencimiento(detalle.getFecha2dovencimiento());
			detalle.setImporte3ervencimiento(detalle.getImporte2dovencimiento());
			detalle.setNroreferenciananterior(detalle.getNroreferencia());
			detalle.setMensajeticket(("agua-" + facturapmc.getClienteId() + "-" + facturapmc.getPeriodoId() + "-"
					+ facturapmc.getPrefijoId() + "-" + facturapmc.getFacturaId() + "-" + facturapmc.getDescripcion())
							.toUpperCase());
			detalle.setMensajepantalla(facturapmc.getDescripcion().toUpperCase());
			detalle.setCodigobarras(facturapmc.getPfcodigo());
			writer.write(detalle.getBarra());
			writer.write("\r\n");
			cantidadregistros++;
			importetotal = importetotal.add(detalle.getImporte1ervencimiento());
		}
		PagoMisCuentasTrailer trailer = new PagoMisCuentasTrailer(9, 400, 462, Tool.hourAbsoluteArgentina(),
				cantidadregistros, importetotal, "");
		writer.write(trailer.getBarra());
		writer.write("\r\n");
		writer.close();
		log.info(file.getAbsolutePath());
		return filename;
	}

}
