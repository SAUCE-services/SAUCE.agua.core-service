/**
 * 
 */
package sauce.agua.rest.service.facade;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;

import sauce.agua.rest.model.Cliente;
import sauce.agua.rest.model.ClienteDato;
import sauce.agua.rest.model.Detalle;
import sauce.agua.rest.model.Factura;
import sauce.agua.rest.model.Medidor;
import sauce.agua.rest.model.Periodo;
import sauce.agua.rest.model.internal.DatoConsumo;
import sauce.agua.rest.service.ClienteDatoService;
import sauce.agua.rest.service.ClienteService;
import sauce.agua.rest.service.DetalleService;
import sauce.agua.rest.service.FacturaService;
import sauce.agua.rest.service.MedidorService;
import sauce.agua.rest.service.PeriodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class LiquidacionService {

	private final Environment env;
	private final JavaMailSender sender;
	private final FacturaService facturaService;
	private final DetalleService detalleService;
	private final ClienteService clienteService;
	private final PeriodoService periodoService;
	private final MedidorService medidorService;
	private final ConsumoService consumoService;
	private final ClienteDatoService clienteDatoService;

	@Setter
    @Getter
    private static class MutableValue<T> {
		private T value;
		
		public MutableValue(T initialValue) {
			this.value = initialValue;
		}

    }

	public LiquidacionService(Environment env, JavaMailSender sender, FacturaService facturaService,
			DetalleService detalleService, ClienteService clienteService, PeriodoService periodoService,
			MedidorService medidorService, ConsumoService consumoService, ClienteDatoService clienteDatoService) {
		this.env = env;
		this.sender = sender;
		this.facturaService = facturaService;
		this.detalleService = detalleService;
		this.clienteService = clienteService;
		this.periodoService = periodoService;
		this.medidorService = medidorService;
		this.consumoService = consumoService;
		this.clienteDatoService = clienteDatoService;
	}

	@Transactional
	public String generatePdf(Integer prefijoId, Long facturaId) {
		String path = env.getProperty("path.files");
		String filename = path + "liquidacion." + prefijoId + "." + facturaId + ".pdf";

		return makePdf(filename, prefijoId, facturaId);
	}

	@Transactional
	public String makePdf(String filename, Integer prefijoId, Long facturaId) {

		Factura factura = facturaService.findByFactura(prefijoId, facturaId);
		Cliente cliente = clienteService.findLastByClienteId(factura.getClienteId());
		Periodo periodo = periodoService.findByPeriodoId(factura.getPeriodoId());
		Periodo periodoNext = periodoService.findByPeriodoId(factura.getPeriodoId() + 1);
		Medidor medidor = medidorService.findByClienteId(factura.getClienteId(), true);
		DatoConsumo datoConsumo = consumoService.calculateConsumo(cliente.getClienteId(), factura.getPeriodoId(),
				medidor.getMedidorId(), factura.getFecha());
		String[] situacion = { "R.I.", "R.N.I.", "Cons.Final", "IVA Exento", "IVA No Resp.", "Monotributo" };
		String[] categoria = { "General", "Especial" };

		var grisClaro = new Color(220, 220, 220);

		try {

			Document document = new Document(new Rectangle(PageSize.A4));
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
			MutableValue<BigDecimal> totalRubros = new MutableValue<>(BigDecimal.ZERO);

			writer.setPageEvent(new FooterPageEvent(cliente, factura, periodo, grisClaro, totalRubros));

			document.setMargins(40, 20, 40, 30);

			document.open();

			// Tabla Cliente
			float[] columnHeader = { 6, 4 };
			PdfPTable tableHeader = new PdfPTable(columnHeader);
			tableHeader.setWidthPercentage(100);

			// Tabla Institucion
			float[] columnInstitucion = { 2, 3 };
			PdfPTable tableInstitucion = new PdfPTable(columnInstitucion);
			tableInstitucion.setWidthPercentage(100);

			Paragraph paragraph = new Paragraph("U.V.S.P.E.S.", new Font(Font.HELVETICA, 18, Font.BOLD));
			PdfPCell cell = new PdfPCell(paragraph);
			cell.setBackgroundColor(grisClaro);
			tableInstitucion.addCell(cell);

			paragraph = new Paragraph(new Phrase("UNIÓN VECINAL DE SERVICIOS PÚBLICOS EL SAUCE", new Font(Font.HELVETICA, 10, Font.BOLD)));
			paragraph.add(new Phrase("\nALFONSO X 47 - EL SAUCE", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase("\nC.P. 5533 - MENDOZA - TEL 261-6532452", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase("\nC.U.I.T. No 30-69577316-8 - ING. BRUTOS EXCEPTUADO", new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase("\nIVA Resp. Inscripto", new Font(Font.HELVETICA, 8)));
			cell = new PdfPCell(paragraph);
			cell.setBackgroundColor(grisClaro);
			tableInstitucion.addCell(cell);
			cell.setBackgroundColor(grisClaro);
			tableHeader.addCell(tableInstitucion);

			paragraph = new Paragraph(MessageFormat.format("Liquidacion: {0}/{1}", factura.getPrefijoId(),
					new DecimalFormat("#0").format(factura.getFacturaId())), new Font(Font.HELVETICA, 8));
			paragraph.add(new Paragraph(
					"\nEmisión: " + DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(factura.getFecha().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 8)));
			paragraph.add(new Paragraph(
					MessageFormat.format("Usuario: {0}, {1}", cliente.getApellido(), cliente.getNombre()),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase(
					"\n" + MessageFormat.format("Domicilio: {0} {1} {2} {3} {4} ({5})", cliente.getInmuebleCalle(),
							cliente.getInmueblePuerta(), cliente.getInmueblePiso(), cliente.getInmuebleDpto(),
							cliente.getInmuebleLocalidad(), cliente.getInmuebleCodpostal()),
					new Font(Font.HELVETICA, 8)));
			paragraph.add(new Phrase("\n" + cliente.getNombreCategoria(), new Font(Font.HELVETICA, 8)));
			paragraph
					.add(new Phrase(
							"\n" + MessageFormat.format("Fiscal: {0} {1} {2} {3} {4} ({5})", cliente.getFiscalCalle(),
									cliente.getFiscalPuerta(), cliente.getFiscalPiso(), cliente.getFiscalDpto(),
									cliente.getFiscalLocalidad(), cliente.getFiscalCodpostal()),
							new Font(Font.HELVETICA, 8)));
			paragraph
					.add(new Phrase(
							"\n" + MessageFormat.format("Situacion: {0} - Socio: {1}",
									situacion[cliente.getSituacionIva() - 1], cliente.getNumeroSocio()),
							new Font(Font.HELVETICA, 8)));
			cell = new PdfPCell(paragraph);
			cell.setBackgroundColor(grisClaro);
			tableHeader.addCell(cell);

			document.add(tableHeader);

			paragraph = new Paragraph("Liquidación de Servicios Públicos Agua/Cloaca",
					new Font(Font.HELVETICA, 12, Font.BOLD));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);

			document.add(new Paragraph(" ", new Font(Font.HELVETICA, 12)));

			// Periodo
			float[] column = { 1, 1, 1, 1, 1 };
			PdfPTable table = new PdfPTable(column);
			table.setWidthPercentage(100);

			paragraph = new Paragraph(MessageFormat.format("Cliente: {0}", cliente.getClienteId()),
					new Font(Font.HELVETICA, 9, Font.BOLD));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(MessageFormat.format("Categoria: {0}", categoria[cliente.getCategoria() - 1]),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			table.addCell(cell);

			paragraph = new Paragraph("Período", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(periodo.getDescripcion(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			table.addCell(cell);
			paragraph = new Paragraph("1er Vencimiento", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(periodo.getFechaPrimero().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			table.addCell(cell);
			paragraph = new Paragraph("2do Vencimiento", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(periodo.getFechaSegundo().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			table.addCell(cell);
			paragraph = new Paragraph("Próximo Vencimiento", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(periodoNext.getFechaPrimero().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			table.addCell(cell);
			document.add(table);

			// Consumo
			float[] columnConsumo = { 1, 1, 1, 1, 1, 1 };
			PdfPTable tableConsumo = new PdfPTable(columnConsumo);
			tableConsumo.setWidthPercentage(100);
			paragraph = new Paragraph("Medidor", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(medidor.getMedidorId(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableConsumo.addCell(cell);
			paragraph = new Paragraph("Fecha Medición", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(datoConsumo.getFechaActual().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableConsumo.addCell(cell);
			paragraph = new Paragraph("Lectura Actual", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(datoConsumo.getEstadoActual().toString(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableConsumo.addCell(cell);
			paragraph = new Paragraph("Fecha Anterior", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(datoConsumo.getFechaAnterior().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableConsumo.addCell(cell);
			paragraph = new Paragraph("Lectura Anterior", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(
					new Phrase(datoConsumo.getEstadoAnterior().toString(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableConsumo.addCell(cell);
			paragraph = new Paragraph("Consumo", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase(datoConsumo.getConsumo().toString(), new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableConsumo.addCell(cell);
			document.add(tableConsumo);

			// Rubros
			float[] columnRubros = { 1, 12, 2, 2, 2 };
			PdfPTable tableRubros = new PdfPTable(columnRubros);
			tableRubros.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("#", new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableRubros.addCell(cell);
			cell = new PdfPCell(new Paragraph("Rubro", new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableRubros.addCell(cell);
			cell = new PdfPCell(new Paragraph("Cantidad", new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableRubros.addCell(cell);
			cell = new PdfPCell(new Paragraph("Unitario", new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableRubros.addCell(cell);
			cell = new PdfPCell(new Paragraph("Subtotal", new Font(Font.HELVETICA, 9, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setLeading(0, 1.5f);
			tableRubros.addCell(cell);

			// Lista rubros
			for (Detalle detalle : detalleService.findAllByFactura(prefijoId, facturaId)) {
				cell = new PdfPCell(new Paragraph(detalle.getRubroId().toString(), new Font(Font.HELVETICA, 9)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0, 1.5f);
				tableRubros.addCell(cell);
				cell = new PdfPCell(new Paragraph(detalle.getConcepto(), new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0, 1.5f);
				tableRubros.addCell(cell);
				cell = new PdfPCell(new Paragraph(new DecimalFormat("#0").format(detalle.getCantidad()),
						new Font(Font.HELVETICA, 9)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0, 1.5f);
				tableRubros.addCell(cell);
				cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(detalle.getPrecioUnitario()),
						new Font(Font.HELVETICA, 9)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0, 1.5f);
				tableRubros.addCell(cell);
				BigDecimal subtotal = detalle.getPrecioUnitario().multiply(detalle.getCantidad()).setScale(2,
						RoundingMode.HALF_UP);
				totalRubros.setValue(totalRubros.getValue().add(subtotal).setScale(2, RoundingMode.HALF_UP));
				cell = new PdfPCell(
						new Paragraph(new DecimalFormat("#,##0.00").format(subtotal), new Font(Font.HELVETICA, 9)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setLeading(0, 1.5f);
				tableRubros.addCell(cell);
			}
			document.add(tableRubros);

			document.add(new Paragraph(" ", new Font(Font.HELVETICA, 12)));

			// Totales y Deuda
			float[] columnTotales = { 5, 2 };
			PdfPTable tableTotales = new PdfPTable(columnTotales);
			tableTotales.setWidthPercentage(100);

			paragraph = new Paragraph("Resumen de Deuda al ", new Font(Font.HELVETICA, 9));
			paragraph.add(new Phrase(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
							.format(factura.getFecha().withOffsetSameInstant(ZoneOffset.UTC)),
					new Font(Font.HELVETICA, 9, Font.BOLD)));
			paragraph.add(new Phrase(" (hasta 6 últimas)", new Font(Font.HELVETICA, 9)));
			cell = new PdfPCell(paragraph);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableTotales.addCell(cell);

			cell = new PdfPCell(new Paragraph(" "));
			cell.setBorder(Rectangle.NO_BORDER);
			tableTotales.addCell(cell);

			// Deuda
			float[] columnDeuda = { 2, 3, 2, 2, 2, 2 };
			PdfPTable tableDeuda = new PdfPTable(columnDeuda);
			tableDeuda.setWidthPercentage(100);

			cell = new PdfPCell(new Paragraph("Liquidación", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			tableDeuda.addCell(cell);
			cell = new PdfPCell(new Paragraph("Período", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			tableDeuda.addCell(cell);
			cell = new PdfPCell(new Paragraph("Vencimiento", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			tableDeuda.addCell(cell);
			cell = new PdfPCell(new Paragraph("Imp.Venc.", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			tableDeuda.addCell(cell);
			cell = new PdfPCell(new Paragraph("Intereses", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			tableDeuda.addCell(cell);
			cell = new PdfPCell(new Paragraph("Total", new Font(Font.HELVETICA, 8, Font.BOLD)));
			cell.setBackgroundColor(grisClaro);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			tableDeuda.addCell(cell);

			for (Factura facturaDeuda : facturaService.findAllByDeudaPrint(factura.getClienteId(),
					factura.getPeriodoId())) {
				cell = new PdfPCell(new Paragraph(
						MessageFormat.format("{0}/{1}", facturaDeuda.getPrefijoId(),
								new DecimalFormat("#0").format(facturaDeuda.getFacturaId())),
						new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tableDeuda.addCell(cell);
				cell = new PdfPCell(
						new Paragraph(facturaDeuda.getPeriodo().getDescripcion(), new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tableDeuda.addCell(cell);
				cell = new PdfPCell(new Paragraph(
						DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
								facturaDeuda.getPeriodo().getFechaPrimero().withOffsetSameInstant(ZoneOffset.UTC)),
						new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				tableDeuda.addCell(cell);
				cell = new PdfPCell(new Paragraph(new DecimalFormat("#,##0.00").format(facturaDeuda.getTotal()),
						new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				tableDeuda.addCell(cell);
				cell = new PdfPCell(new Paragraph(
						new DecimalFormat("#,##0.00")
								.format(facturaDeuda.getInteres().setScale(2, RoundingMode.HALF_UP)),
						new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				tableDeuda.addCell(cell);
				cell = new PdfPCell(
						new Paragraph(
								new DecimalFormat("#,##0.00").format(facturaDeuda.getTotal()
										.add(facturaDeuda.getInteres()).setScale(2, RoundingMode.HALF_UP)),
								new Font(Font.HELVETICA, 8)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.NO_BORDER);
				tableDeuda.addCell(cell);
			}

			cell = new PdfPCell(tableDeuda);
			tableTotales.addCell(cell);

			// Totales
			paragraph = new Paragraph("Total Rubros: ", new Font(Font.HELVETICA, 9));
			paragraph.add(
					new Phrase(new DecimalFormat("#,##0.00").format(totalRubros.getValue()), new Font(Font.HELVETICA, 11, Font.BOLD)));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase("IVA CF: ", new Font(Font.HELVETICA, 9)));
			paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(factura.getIvaCf()),
					new Font(Font.HELVETICA, 11, Font.BOLD)));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase("IVA RI: ", new Font(Font.HELVETICA, 9)));
			paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(factura.getIvaRi()),
					new Font(Font.HELVETICA, 11, Font.BOLD)));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase("IVA RN: ", new Font(Font.HELVETICA, 9)));
			paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(factura.getIvaRn()),
					new Font(Font.HELVETICA, 11, Font.BOLD)));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase("Total 1er Vencimiento: ", new Font(Font.HELVETICA, 9)));
			paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(factura.getTotal()),
					new Font(Font.HELVETICA, 11, Font.BOLD)));
			paragraph.add(new Phrase("\n"));
			paragraph.add(new Phrase("Total 2do Vencimiento: ", new Font(Font.HELVETICA, 9)));
			paragraph.add(new Phrase(
					new DecimalFormat("#,##0.00")
							.format(factura.getTotal().add(factura.getInteres()).setScale(2, RoundingMode.HALF_UP)),
					new Font(Font.HELVETICA, 11, Font.BOLD)));
			cell = new PdfPCell(paragraph);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setLeading(0, 1.2f);
			tableTotales.addCell(cell);
			document.add(tableTotales);

			// Código de Barras
			BarcodeInter25 code25 = new BarcodeInter25();
			code25.setGenerateChecksum(false);
			code25.setCode(factura.getPfCodigo());
			code25.setX(1.3f);

			Image image = code25.createImageWithBarcode(writer.getDirectContent(), null, null);
			image.setAlignment(Element.ALIGN_CENTER);
			document.add(image);

			//
			document.close();
		} catch (FileNotFoundException e) {
			log.debug("FileNotFoundException: {}", e.getMessage());
		}

		return filename;
	}

	private class FooterPageEvent extends PdfPageEventHelper {

		private final Cliente cliente;
		private final Factura factura;
		private final Periodo periodo;
		private final Color grisClaro;
		private final MutableValue<BigDecimal> totalRubros;

		public FooterPageEvent(Cliente cliente,
							   Factura factura,
							   Periodo periodo,
							   Color grisClaro,
							   MutableValue<BigDecimal> totalRubros) {
			this.cliente = cliente;
			this.factura = factura;
			this.periodo = periodo;
			this.grisClaro = grisClaro;
			this.totalRubros = totalRubros;
		}

		public void onEndPage(PdfWriter writer, Document document) {
			try {
				PdfPTable footer = new PdfPTable(2);
				footer.setWidthPercentage(100);
				footer.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
				
				var paragraph = new Paragraph(new Phrase("U.V.S.P.E.S.", new Font(Font.HELVETICA, 9, Font.BOLD)));
				paragraph.add(new Phrase(" Unión Vecinal de Servicios Públicos El Sauce", new Font(Font.HELVETICA, 8)));
				var cell = new PdfPCell(paragraph);
				footer.addCell(cell);

				footer.addCell(cell);

				// Cliente
				float[] widthCliente = { 1, 1, 2 };
				PdfPTable tableCliente = new PdfPTable(widthCliente);
				tableCliente.setWidthPercentage(100);
				paragraph = new Paragraph(new Phrase("Liquidación", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				tableCliente.addCell(cell);
				paragraph = new Paragraph(new Phrase("Emisión", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				tableCliente.addCell(cell);
				paragraph = new Paragraph(new Phrase("Usuario", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				tableCliente.addCell(cell);
				footer.addCell(tableCliente);

				footer.addCell(tableCliente);

				tableCliente = new PdfPTable(widthCliente);
				tableCliente.setWidthPercentage(100);
				paragraph = new Paragraph(new Phrase(MessageFormat.format("{0}/{1}", factura.getPrefijoId(),
						new DecimalFormat("#0").format(factura.getFacturaId())), new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell = new PdfPCell(paragraph);
				tableCliente.addCell(cell);
				paragraph = new Paragraph(DateTimeFormatter.ofPattern("dd/MM/yyyy")
						.format(factura.getFecha().withOffsetSameInstant(ZoneOffset.UTC)), new Font(Font.HELVETICA, 8, Font.BOLD));
				cell = new PdfPCell(paragraph);
				tableCliente.addCell(cell);
				paragraph = new Paragraph(MessageFormat.format("{0}, {1}", cliente.getApellido(), cliente.getNombre()), new Font(Font.HELVETICA, 8, Font.BOLD));
				cell = new PdfPCell(paragraph);
				tableCliente.addCell(cell);
				footer.addCell(tableCliente);

				footer.addCell(tableCliente);

				// Datos
				var footerData = new PdfPTable(4);
				footerData.setWidthPercentage(100);
				paragraph = new Paragraph(new Phrase("Cliente", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				paragraph = new Paragraph(new Phrase("Periodo", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				paragraph = new Paragraph(new Phrase("1er Venc.", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				paragraph = new Paragraph(new Phrase("2do Venc.", new Font(Font.HELVETICA, 8)));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				footer.addCell(footerData);

				footer.addCell(footerData);

				footerData = new PdfPTable(4);
				footerData.setWidthPercentage(100);
				paragraph = new Paragraph(new Phrase(cliente.getClienteId().toString(), new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				paragraph = new Paragraph(new Phrase(periodo.getDescripcion(), new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell = new PdfPCell(paragraph);
				cell.setNoWrap(true);
				footerData.addCell(cell);
				paragraph = new Paragraph(new Phrase(DateTimeFormatter.ofPattern("dd/MM/yyyy")
						.format(periodo.getFechaPrimero().withOffsetSameInstant(ZoneOffset.UTC)), new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				paragraph = new Paragraph(new Phrase(DateTimeFormatter.ofPattern("dd/MM/yyyy")
						.format(periodo.getFechaSegundo().withOffsetSameInstant(ZoneOffset.UTC)), new Font(Font.HELVETICA, 8, Font.BOLD)));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				footer.addCell(footerData);

				footer.addCell(footerData);

				footerData = new PdfPTable(4);
				footerData.setWidthPercentage(100);
				paragraph = new Paragraph("Servicio", new Font(Font.HELVETICA, 8));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				paragraph = new Paragraph("IVA", new Font(Font.HELVETICA, 8));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				paragraph = new Paragraph("Importe", new Font(Font.HELVETICA, 8));
				cell = new PdfPCell(paragraph);
				cell.setBackgroundColor(grisClaro);
				footerData.addCell(cell);
				footerData.addCell(cell);
				footer.addCell(footerData);

				footer.addCell(footerData);

				footerData = new PdfPTable(4);
				footerData.setWidthPercentage(100);
				paragraph = new Paragraph(new DecimalFormat("#,##0.00").format(totalRubros.getValue()), new Font(Font.HELVETICA, 8, Font.BOLD));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				paragraph = new Paragraph(new DecimalFormat("#,##0.00").format(factura.getIvaCf().add(factura.getIvaRi()).add(factura.getIvaRn())), new Font(Font.HELVETICA, 8, Font.BOLD));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				paragraph = new Paragraph(new DecimalFormat("#,##0.00").format(factura.getTotal()), new Font(Font.HELVETICA, 8, Font.BOLD));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				paragraph = new Paragraph(new DecimalFormat("#,##0.00")
						.format(factura.getTotal().add(factura.getInteres()).setScale(2, RoundingMode.HALF_UP)), new Font(Font.HELVETICA, 8, Font.BOLD));
				cell = new PdfPCell(paragraph);
				footerData.addCell(cell);
				footer.addCell(footerData);

				footer.addCell(footerData);

				footer.writeSelectedRows(0, -1, document.leftMargin(),
						document.bottomMargin() + 100, writer.getDirectContent());
			} catch (Exception e) {
				log.error("Error al generar footer: {}", e.getMessage());
			}
		}
	}

	@Transactional
	public String sendLiquidacion(Integer prefijoId, Long facturaId) throws MessagingException {
		String filenameLiquidacion = this.generatePdf(prefijoId, facturaId);
        log.info("Filename_liquidacion -> {}", filenameLiquidacion);
		if (filenameLiquidacion.isEmpty()) {
			return "ERROR: Sin Liquidación para ENVIAR";
		}

		String data = "";

		data = "Estimad@ " + ": " + (char) 10;
		data = data + (char) 10;
		data = data + "Le enviamos como archivo adjunto su liquidación del servicio de agua." + (char) 10;
		data = data + (char) 10;
		data = data + "Atentamente." + (char) 10;
		data = data + (char) 10;
		data = data + "Unión Vecinal de Servicios Públicos 'El Sauce'" + (char) 10;
		data = data + (char) 10;
		data = data + (char) 10
				+ "Por favor no responda este mail, fue generado automáticamente. Su respuesta no será leída."
				+ (char) 10;

		// Envia correo
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		List<String> addresses = new ArrayList<String>();
		Factura factura = facturaService.findByFactura(prefijoId, facturaId);
		ClienteDato clienteDato = clienteDatoService.findByClienteId(factura.getClienteId());
		addresses.add(clienteDato.getEmail());

		try {
			helper.setTo(addresses.toArray(new String[0]));
			helper.setText(data);
			helper.setSubject("Envío Automático de Liquidación de Consumo de Agua -> " + filenameLiquidacion);

			FileSystemResource fileBono = new FileSystemResource(filenameLiquidacion);
			helper.addAttachment(filenameLiquidacion, fileBono);

		} catch (MessagingException e) {
			log.debug("MessagingException: {}", e.getMessage());
			return "ERROR: No pudo ENVIARSE";
		}

		sender.send(message);

		return "Envío de Correo Ok!!";
	}

}
