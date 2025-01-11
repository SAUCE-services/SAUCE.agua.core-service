/**
 * 
 */
package sauce.agua.rest.exception;

import java.text.MessageFormat;
import java.time.OffsetDateTime;

/**
 * @author daniel
 *
 */
public class DesconexionException extends RuntimeException {

	private static final long serialVersionUID = -2282384956265605136L;

	public DesconexionException(Long clienteId, OffsetDateTime fechaEmision) {
		super(MessageFormat.format("Cannot find Desconexion {0}/{1}", clienteId, fechaEmision));
	}

}
