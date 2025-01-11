/**
 * 
 */
package sauce.agua.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class MedicionException extends RuntimeException {

	private static final long serialVersionUID = 1455630438897018763L;

	public MedicionException(Long uniqueId) {
		super(MessageFormat.format("Cannot find Medicion {0}", uniqueId));
	}

	public MedicionException(Long clienteId, Integer periodoId) {
		super(MessageFormat.format("Cannot find Medicion {0}/{1}", clienteId, periodoId));
	}

}
