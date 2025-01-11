/**
 * 
 */
package sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class TipoNotificacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4369292512771932328L;

	public TipoNotificacionException(Integer tiponotificacionId) {
		super("Cannot find TipoNotificacion " + tiponotificacionId);
	}

}
