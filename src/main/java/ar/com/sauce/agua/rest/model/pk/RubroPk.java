/**
 * 
 */
package ar.com.sauce.agua.rest.model.pk;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class RubroPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7634033454112408188L;

	private Integer rubroId;
	private LocalDate fecha;

}
