/**
 * 
 */
package sauce.agua.rest.model.view.pk;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class PeriodoPagoPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5427509167117051769L;

	private Integer periodoId;
	private OffsetDateTime fechapago;
}
