/**
 * 
 */
package ar.com.sauce.agua.rest.misc;

import java.io.Serializable;
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
public class PagoMisCuentasHeader implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1970934680475219969L;

	private Integer codigoregistro;
	private Integer codigobanelco;
	private Integer codigoempresa;
	private OffsetDateTime fecha;
	private String registro;

	public String getBarra() {
		String string = "";
		string += codigoregistro;
		string += String.format("%03d", codigobanelco);
		string += String.format("%04d", codigoempresa);
		string += fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		string += String.format("%1$" + 264 + "s", "").replace(' ', '0');
		return string;
	}
}
