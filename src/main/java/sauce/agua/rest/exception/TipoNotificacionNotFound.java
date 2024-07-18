/**
 * 
 */
package sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class TipoNotificacionNotFound extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4369292512771932328L;

	public TipoNotificacionNotFound(Integer tiponotificacionId) {
		super("Cannot find TipoNotificacion " + tiponotificacionId);
	}

}
