/**
 * 
 */
package sauce.agua.rest.exception;

import java.time.OffsetDateTime;

/**
 * @author daniel
 *
 */
public class NotificacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1341650401082445533L;

	public NotificacionException(Long clienteId, OffsetDateTime fecha) {
		super("Cannot find Notificacion " + clienteId + "/" + fecha);
	}

}
