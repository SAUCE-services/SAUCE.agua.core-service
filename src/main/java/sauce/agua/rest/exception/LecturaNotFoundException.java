/**
 * 
 */
package sauce.agua.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class LecturaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8358977640282355219L;

	public LecturaNotFoundException(String medidorId, Integer periodoId) {
		super(MessageFormat.format("Cannot find Lectura {0}/{1}", medidorId, periodoId));
	}

}
