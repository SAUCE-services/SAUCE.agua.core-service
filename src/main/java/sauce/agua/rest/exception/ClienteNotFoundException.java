/**
 * 
 */
package sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class ClienteNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4206713638161142667L;

	public ClienteNotFoundException(Long uniqueId) {
		super("Cannot find Cliente " + uniqueId);
	}

	public ClienteNotFoundException() {
		super("Cannot find Cliente ");
	}

}
