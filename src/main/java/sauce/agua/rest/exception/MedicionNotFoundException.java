/**
 * 
 */
package sauce.agua.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class MedicionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1455630438897018763L;

	public MedicionNotFoundException(Long uniqueId) {
		super(MessageFormat.format("Cannot find Medicion {0}", uniqueId));
	}

	public MedicionNotFoundException(Long clienteId, Integer periodoId) {
		super(MessageFormat.format("Cannot find Medicion {0}/{1}", clienteId, periodoId));
	}

}
