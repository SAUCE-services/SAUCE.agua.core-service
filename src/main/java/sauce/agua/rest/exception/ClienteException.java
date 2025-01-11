/**
 * 
 */
package sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class ClienteException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4206713638161142667L;

	public ClienteException(Long uniqueId) {
		super("Cannot find Cliente " + uniqueId);
	}

	public ClienteException() {
		super("Cannot find Cliente ");
	}

}
