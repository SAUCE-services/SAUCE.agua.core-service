/**
 * 
 */
package ar.com.sauce.agua.rest.misc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoMisCuentasDetalle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2799971202354726239L;

	private Integer codigoregistro;
	private String nroreferencia;
	private String idfactura;
	private Integer codigomoneda;
	private OffsetDateTime fecha1ervencimiento;
	private OffsetDateTime fechaaplicacion;
	private BigDecimal importe1ervencimiento;
	private OffsetDateTime fecha2dovencimiento;
	private BigDecimal importe2dovencimiento;
	private OffsetDateTime fecha3ervencimiento;
	private BigDecimal importe3ervencimiento;
	private Integer codigomovimiento;
	private OffsetDateTime fechaacreditacion;
	private String canalpago;
	private String nrocontrol;
	private String nroreferenciananterior;
	private String codigoprovincia;
	private String mensajeticket;
	private String mensajepantalla;
	private String codigobarras;
	private String registro;
	
	public String getBarra() {
		String string = "";
		string += codigoregistro;
		string += (nroreferencia + String.format("%1$" + 19 + "s", "")).substring(0, 19);
		string += (idfactura + String.format("%1$" + 20 + "s", "")).substring(0, 20);
		string += codigomoneda;
		string += fecha1ervencimiento.plusHours(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		string += new DecimalFormat("00000000000").format(importe1ervencimiento.multiply(new BigDecimal(100)));
		string += fecha2dovencimiento.plusHours(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		string += new DecimalFormat("00000000000").format(importe2dovencimiento.multiply(new BigDecimal(100)));
		string += fecha3ervencimiento.plusHours(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		string += new DecimalFormat("00000000000").format(importe3ervencimiento.multiply(new BigDecimal(100)));
		string += String.format("%1$" + 19 + "s", "").replace(' ', '0');
		string += (nroreferenciananterior + String.format("%1$" + 19 + "s", "")).substring(0, 19);
		string += (mensajeticket + String.format("%1$" + 40 + "s", "")).substring(0, 40);
		string += (mensajepantalla + String.format("%1$" + 15 + "s", "")).substring(0, 15);
		string += (codigobarras + String.format("%1$" + 60 + "s", "")).substring(0, 60);
		string += String.format("%1$" + 29 + "s", "").replace(' ', '0');
		return string;
	}
	
}
