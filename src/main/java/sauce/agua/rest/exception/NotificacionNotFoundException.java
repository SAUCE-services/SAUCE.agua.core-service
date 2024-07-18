/**
 * 
 */
package sauce.agua.rest.exception;

import java.time.OffsetDateTime;

/**
 * @author daniel
 *
 */
public class NotificacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1341650401082445533L;

	public NotificacionNotFoundException(Long clienteId, OffsetDateTime fecha) {
		super("Cannot find Notificacion " + clienteId + "/" + fecha);
	}

}
