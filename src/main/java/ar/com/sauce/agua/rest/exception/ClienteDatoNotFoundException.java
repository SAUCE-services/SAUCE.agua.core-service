/**
 * 
 */
package ar.com.sauce.agua.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class ClienteDatoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6031808824867380871L;

	public ClienteDatoNotFoundException(Long clienteId) {
		super(MessageFormat.format("Cannot find ClienteDato {0}", clienteId));
	}

}
