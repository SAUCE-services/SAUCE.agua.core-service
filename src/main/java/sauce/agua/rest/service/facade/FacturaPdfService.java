package sauce.agua.rest.service.facade;

import com.github.openjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpdf.text.*;
import org.openpdf.text.Font;
import org.openpdf.text.Image;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import sauce.agua.rest.hexagonal.operador.application.service.OperadorService;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.model.Cliente;
import sauce.agua.rest.model.Factura;
import sauce.agua.rest.service.ClienteService;
import sauce.agua.rest.service.FacturaService;
import sauce.agua.rest.util.Tool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacturaPdfService {

    private final Environment environment;
    private final ClienteService clienteService;
    private final OperadorService operadorService;
    private final FacturaService facturaService;

    private void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
            throws WriterException, IOException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, fileType, qrFile);
    }

//    public String generatePdf(Integer prefijoId, Long facturaId) {
//
//        Image imageQr = null;
//        Operador operador = operadorService.findLast();
//        Factura factura = facturaService.findByFactura(prefijoId, facturaId);
//        Cliente cliente = clienteService.findLastByClienteId(factura.getClienteId());
//        Electronico electronico = electronicoService.findByUnique(clienteMovimiento.getComprobanteId(),
//                clienteMovimiento.getPuntoventa(), clienteMovimiento.getNumerocomprobante());
//
//        ClienteMovimiento clienteMovimientoAsociado = null;
//        ComprobanteAfip comprobanteAfipAsociado = null;
//        if (electronico.getClientemovimientoIdasociado() != null) {
//            clienteMovimientoAsociado = clienteMovimientoService
//                    .findByClienteMovimientoId(electronico.getClientemovimientoIdasociado());
//            log.debug("ClienteMovimientoAsociado -> {}", clienteMovimientoAsociado);
//            Comprobante comprobante = comprobanteService
//                    .findByComprobanteId(clienteMovimientoAsociado.getComprobanteId());
//            comprobanteAfipAsociado = comprobanteAfipService
//                    .findByComprobanteafipId(comprobante.getComprobanteafipId());
//        }
//
//        String path = environment.getProperty("path.facturas");
//
//        try {
//            String url = "https://www.afip.gob.ar/fe/qr/?p=";
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("ver", 1);
//            jsonObject.put("fecha", DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                    .format(Tool.stringDDMMYYYY2OffsetDateTime(Objects.requireNonNull(electronico.getFecha()))));
//            jsonObject.put("cuit", Long.parseLong(Objects.requireNonNull(empresa.getCuit()).replaceAll("-", "")));
//            jsonObject.put("ptoVta", electronico.getPuntoventa());
//            jsonObject.put("tipoCmp", electronico.getComprobanteId());
//            jsonObject.put("nroCmp", electronico.getNumerocomprobante());
//            jsonObject.put("importe", electronico.getTotal());
//            jsonObject.put("moneda", "PES");
//            jsonObject.put("ctz", 1);
//            jsonObject.put("tipoDocRec", electronico.getTipodocumento());
//            jsonObject.put("nroDocRec", electronico.getNumerodocumento());
//            jsonObject.put("tipoCodAut", "E");
//            jsonObject.put("codAut", new BigDecimal(electronico.getCae()));
//            String datos = new String(Base64.getEncoder().encode(jsonObject.toString().getBytes()));
//            String fileType = "png";
//            String filePath = path + electronico.getCae() + "." + fileType;
//            int size = 150;
//            File qrFile = new File(filePath);
//            createQRImage(qrFile, url + datos, size, fileType);
//            imageQr = Image.getInstance(filePath);
//        } catch (BadElementException | WriterException | IOException e) {
//            log.debug("Sin Imagen");
//        }
//
//        Comprobante comprobante = comprobanteService.findByComprobanteId(electronico.getComprobanteId());
//        Boolean discrimina = true;
//        int copias = 2;
//        List<String> discriminados = Arrays.asList("A", "M");
//        if (discriminados.contains(comprobante.getLetracomprobante())) {
//            discrimina = true;
//        }
//        ComprobanteAfip comprobanteAfip = comprobanteAfipService
//                .findByComprobanteafipId(comprobante.getComprobanteafipId());
//
//        String[] titulo_copias = {"ORIGINAL", "DUPLICADO", "TRIPLICADO"};
//
//        String filename = "";
//        List<String> filenames = new ArrayList<>();
//        for (int copia = 0; copia < copias; copia++) {
//            filenames.add(filename = path + clientemovimientoId + "." + titulo_copias[copia].toLowerCase() + ".pdf");
//
//            makePage(filename, titulo_copias[copia], empresa, comprobante, comprobanteAfip, electronico, cliente,
//                    clienteMovimiento, discrimina, imageQr, clienteMovimientoAsociado, comprobanteAfipAsociado);
//        }
//
//        try {
//            mergePdf(filename = path + clientemovimientoId + ".pdf", filenames);
//        } catch (DocumentException | IOException e) {
//            log.debug(e.getMessage());
//        }
//
//        return filename;
//    }
//
//    private void mergePdf(String filename, List<String> filenames) throws DocumentException, IOException {
//        OutputStream outputStream = new FileOutputStream(new File(filename));
//        Document document = new Document();
//        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
//        document.open();
//        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
//        for (String name : filenames) {
//            PdfReader pdfReader = new PdfReader(new FileInputStream(new File(name)));
//            for (int pagina = 0; pagina < pdfReader.getNumberOfPages(); ) {
//                document.newPage();
//                PdfImportedPage page = pdfWriter.getImportedPage(pdfReader, ++pagina);
//                pdfContentByte.addTemplate(page, 0, 0);
//            }
//        }
//        outputStream.flush();
//        document.close();
//        outputStream.close();
//    }
//
//    private void makePage(String filename, String titulo, Empresa empresa, Comprobante comprobante,
//                          ComprobanteAfip comprobanteAfip, Electronico electronico, Cliente cliente,
//                          ClienteMovimiento clienteMovimiento, Boolean discriminar, Image imageQr,
//                          ClienteMovimiento clienteMovimientoAsociado, ComprobanteAfip comprobanteAfipAsociado) {
//        PdfPTable table = null;
//        PdfPCell cell = null;
//
//        Document document = new Document(new Rectangle(PageSize.A4));
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(filename));
//            document.setMargins(20, 20, 20, 20);
//            document.open();
//
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100);
//            Paragraph paragraph = new Paragraph(titulo, new Font(Font.HELVETICA, 16, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            cell = new PdfPCell();
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            document.add(table);
//
//            table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            table.setWidths(new int[]{48, 4, 48});
//            cell = new PdfPCell();
//            paragraph = new Paragraph(empresa.getNombrefantasia(), new Font(Font.HELVETICA, 14, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(10);
//            cell.addElement(paragraph);
//            cell.addElement(new Paragraph(" ", new Font(Font.HELVETICA, 6, Font.NORMAL)));
//            paragraph = new Paragraph(new Phrase("Razón Social: ", new Font(Font.HELVETICA, 9, Font.NORMAL)));
//            paragraph.add(new Phrase(empresa.getRazonsocial(), new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(10);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Domicilio Comercial: ", new Font(Font.HELVETICA, 9, Font.NORMAL)));
//            paragraph.add(new Phrase(empresa.getDomicilio() + " " + empresa.getTelefono(),
//                    new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(10);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(
//                    new Phrase("Condición frente al IVA: ", new Font(Font.HELVETICA, 9, Font.NORMAL)));
//            paragraph.add(new Phrase(empresa.getCondicioniva(), new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(10);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            paragraph = new Paragraph(comprobante.getLetracomprobante(), new Font(Font.HELVETICA, 24, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Cod: ", new Font(Font.HELVETICA, 6, Font.NORMAL)));
//            paragraph.add(
//                    new Phrase(Objects.requireNonNull(comprobante.getComprobanteafipId()).toString(), new Font(Font.HELVETICA, 6, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            paragraph = new Paragraph(comprobanteAfip.getLabel(), new Font(Font.HELVETICA, 14, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Punto de Venta: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(new DecimalFormat("0000").format(electronico.getPuntoventa()),
//                    new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.add(new Phrase("          Comprobante Nro: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(new DecimalFormat("00000000").format(electronico.getNumerocomprobante()),
//                    new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Fecha de Emisión: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(Tool.stringDDMMYYYY2OffsetDateTime(Objects.requireNonNull(electronico.getFecha()))
//                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("CUIT: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(empresa.getCuit(), new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Ingresos Brutos: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(empresa.getIngresosbrutos(), new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Inicio Actividades: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(empresa.getInicioactividades(), new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            document.add(table);
//
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100);
//            cell = new PdfPCell();
//            paragraph = new Paragraph(new Phrase("Cliente: ", new Font(Font.HELVETICA, 10, Font.NORMAL)));
//            paragraph.add(new Phrase(cliente.getRazonsocial(), new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Domicilio: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(cliente.getDomicilio(), new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("CUIT: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(cliente.getCuit(), new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.add(new Phrase("                          IVA: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            String[] condiciones = {"Responsable Inscripto", "Consumidor Final", "Monotributista",
//                    "Responsable No Inscripto", "Exento", "Exportación"};
//            paragraph
//                    .add(new Phrase(condiciones[cliente.getPosicioniva() - 1], new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            paragraph = new Paragraph(new Phrase("Condición de venta: ", new Font(Font.HELVETICA, 8, Font.NORMAL)));
//            paragraph.add(new Phrase(comprobante.getContado() == 0 ? "Cuenta Corriente" : "Contado",
//                    new Font(Font.HELVETICA, 8, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            paragraph.setIndentationLeft(20);
//            cell.addElement(paragraph);
//            cell.addElement(new Paragraph(" ", new Font(Font.HELVETICA, 6, Font.BOLD)));
//            table.addCell(cell);
//            document.add(table);
//
//            table = new PdfPTable(5);
//            table.setWidthPercentage(100);
//            table.setWidths(new int[]{10, 50, 7, 12, 12});
//            cell = new PdfPCell();
//            paragraph = new Paragraph("Código", new Font(Font.HELVETICA, 8, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            paragraph = new Paragraph("Artículo", new Font(Font.HELVETICA, 8, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            paragraph = new Paragraph("Cantidad", new Font(Font.HELVETICA, 8, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_RIGHT);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            paragraph = new Paragraph("Precio Unitario", new Font(Font.HELVETICA, 8, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_RIGHT);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            cell = new PdfPCell();
//            paragraph = new Paragraph("Subtotal", new Font(Font.HELVETICA, 8, Font.BOLD));
//            paragraph.setAlignment(Element.ALIGN_RIGHT);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            document.add(table);
//
//            int lineas = 24;
//
//            for (ArticuloMovimiento articulomovimiento : articuloMovimientoService
//                    .findAllByClientemovimientoId(clienteMovimiento.getClientemovimientoId())) {
//                lineas--;
//                table = new PdfPTable(5);
//                table.setWidthPercentage(100);
//                table.setWidths(new int[]{10, 50, 7, 12, 12});
//                cell = new PdfPCell();
//                cell.setBorder(Rectangle.NO_BORDER);
//                String codigoId = articulomovimiento.getArticuloId();
//                assert codigoId != null;
//                String[] codigos = codigoId.split("\\.");
//                paragraph = new Paragraph(codigos[0], new Font(Font.HELVETICA, 8, Font.NORMAL));
//                paragraph.setAlignment(Element.ALIGN_CENTER);
//                cell.addElement(paragraph);
//                table.addCell(cell);
//                cell = new PdfPCell();
//                cell.setBorder(Rectangle.NO_BORDER);
//                Articulo articulo = articuloService.findByArticuloId(articulomovimiento.getArticuloId());
//                paragraph = new Paragraph(articulo.getDescripcion(), new Font(Font.HELVETICA, 8, Font.NORMAL));
//                paragraph.setAlignment(Element.ALIGN_LEFT);
//                cell.addElement(paragraph);
//                table.addCell(cell);
//                cell = new PdfPCell();
//                cell.setBorder(Rectangle.NO_BORDER);
//                paragraph = new Paragraph(String.valueOf(Math.abs(articulomovimiento.getCantidad().intValue())),
//                        new Font(Font.HELVETICA, 8, Font.NORMAL));
//                paragraph.setAlignment(Element.ALIGN_RIGHT);
//                cell.addElement(paragraph);
//                table.addCell(cell);
//                cell = new PdfPCell();
//                cell.setBorder(Rectangle.NO_BORDER);
//                paragraph = new Paragraph(
//                        new DecimalFormat("#,##0.00").format(discriminar ? articulomovimiento.getPreciodescuentosiniva()
//                                : articulomovimiento.getPreciodescuentoconiva()),
//                        new Font(Font.HELVETICA, 8, Font.NORMAL));
//                paragraph.setAlignment(Element.ALIGN_RIGHT);
//                cell.addElement(paragraph);
//                table.addCell(cell);
//                cell = new PdfPCell();
//                cell.setBorder(Rectangle.NO_BORDER);
//                paragraph = new Paragraph(
//                        new DecimalFormat("#,##0.00").format(Math.abs(articulomovimiento.getCantidad()
//                                .multiply((discriminar ? articulomovimiento.getPreciodescuentosiniva()
//                                        : articulomovimiento.getPreciodescuentoconiva()))
//                                .doubleValue())),
//                        new Font(Font.HELVETICA, 8, Font.NORMAL));
//                paragraph.setAlignment(Element.ALIGN_RIGHT);
//                cell.addElement(paragraph);
//                table.addCell(cell);
//                document.add(table);
//            }
//
//            for (int i = 0; i < lineas; i++) {
//                table = new PdfPTable(1);
//                table.setWidthPercentage(100);
//                cell = new PdfPCell();
//                cell.setBorder(Rectangle.NO_BORDER);
//                cell.addElement(new Paragraph("  ", new Font(Font.COURIER, 8, Font.NORMAL)));
//                table.addCell(cell);
//                document.add(table);
//            }
//
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100);
//            paragraph = new Paragraph(new Phrase("Observaciones: ", new Font(Font.COURIER, 10, Font.BOLD)));
//            String observaciones = "";
//            if (electronico.getClientemovimientoIdasociado() != null) {
//                observaciones += "Asoc: ";
//                if (comprobanteAfipAsociado != null) {
//                    observaciones += comprobanteAfipAsociado.getLabel();
//                }
//                if (clienteMovimientoAsociado != null) {
//                    observaciones += clienteMovimientoAsociado.getTipocomprobante()
//                            + String.format("%04d", clienteMovimientoAsociado.getPuntoventa()) + "-"
//                            + String.format("%08d", clienteMovimientoAsociado.getNumerocomprobante());
//                }
//            }
//            observaciones += clienteMovimiento.getObservaciones();
//            paragraph.add(new Phrase(observaciones, new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_LEFT);
//            cell = new PdfPCell();
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            document.add(table);
//
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100);
//            cell = new PdfPCell();
//            if (discriminar) {
//                paragraph = new Paragraph(new Phrase("Importe Neto: $ ", new Font(Font.COURIER, 9, Font.NORMAL)));
//                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getNeto().abs()),
//                        new Font(Font.HELVETICA, 9, Font.BOLD)));
//                paragraph.setAlignment(Element.ALIGN_RIGHT);
//                cell.addElement(paragraph);
//
//                paragraph = new Paragraph(new Phrase("Importe IVA: $ ", new Font(Font.COURIER, 9, Font.NORMAL)));
//                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getIva().abs()),
//                        new Font(Font.HELVETICA, 9, Font.BOLD)));
//                paragraph.setAlignment(Element.ALIGN_RIGHT);
//                cell.addElement(paragraph);
//
//                paragraph = new Paragraph(new Phrase("Importe Exento: $ ", new Font(Font.COURIER, 9, Font.NORMAL)));
//                paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getExento().abs()),
//                        new Font(Font.HELVETICA, 9, Font.BOLD)));
//                paragraph.setAlignment(Element.ALIGN_RIGHT);
//                cell.addElement(paragraph);
//            }
//            paragraph = new Paragraph(new Phrase("Importe Total: $ ", new Font(Font.COURIER, 10, Font.BOLD)));
//            paragraph.add(new Phrase(new DecimalFormat("#,##0.00").format(clienteMovimiento.getImporte().abs()),
//                    new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.setAlignment(Element.ALIGN_RIGHT);
//            cell.addElement(paragraph);
//            table.addCell(cell);
//            document.add(table);
//
//            // Datos CAE
//            float[] columnCAE = {1, 3};
//            PdfPTable tableCAE = new PdfPTable(columnCAE);
//            tableCAE.setWidthPercentage(100);
//
//            // Agrega código QR
//            cell = new PdfPCell();
//            cell.addElement(imageQr);
//            cell.setBorder(Rectangle.NO_BORDER);
//            tableCAE.addCell(cell);
//            //
//
//            paragraph = new Paragraph("CAE Nro: ", new Font(Font.COURIER, 10, Font.NORMAL));
//            paragraph.add(new Phrase(electronico.getCae(), new Font(Font.HELVETICA, 10, Font.BOLD)));
//            paragraph.add(new Phrase("\n", new Font(Font.COURIER, 10, Font.NORMAL)));
//            paragraph.add(new Phrase("Vencimiento CAE: ", new Font(Font.COURIER, 10, Font.NORMAL)));
//            paragraph.add(new Phrase(electronico.getCaevencimiento(), new Font(Font.HELVETICA, 10, Font.BOLD)));
//            cell = new PdfPCell(paragraph);
//            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setBorder(Rectangle.NO_BORDER);
//            cell.setLeading(0, 1.5f);
//            tableCAE.addCell(cell);
//            document.add(tableCAE);
//            document.close();
//        } catch (Exception ex) {
//            log.debug(ex.getMessage());
//        }
//
//    }

}
