/**
 * 
 */
package ar.com.sauce.agua.rest.service.view;

/**
 * @author daniel
 *
 */
public class ClienteRecorridoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2398735215768261692L;

	public ClienteRecorridoNotFoundException(Long clienteId) {
		super("Cannot find ClienteRecorrido " + clienteId);
	}

}
