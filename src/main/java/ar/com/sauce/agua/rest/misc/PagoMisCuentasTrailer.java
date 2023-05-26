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
public class PagoMisCuentasTrailer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5059006590793129123L;

	private Integer codigoregistro;
	private Integer codigobanelco;
	private Integer codigoempresa;
	private OffsetDateTime fecha;
	private Integer cantidadregistros;
	private BigDecimal totalimporte;
	private String registro;

	public String getBarra() {
		String string = "";
		string += codigoregistro;
		string += String.format("%03d", codigobanelco);
		string += String.format("%04d", codigoempresa);
		string += fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		string += String.format("%07d", cantidadregistros);
		string += String.format("%1$" + 7 + "s", "").replace(' ', '0');
		string += new DecimalFormat("0000000000000000").format(totalimporte.multiply(new BigDecimal(100)));
		string += String.format("%1$" + 234 + "s", "").replace(' ', '0');
		return string;
	}

}
