/**
 * 
 */
package sauce.agua.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class MedidorException extends RuntimeException {

	private static final long serialVersionUID = -832277136303789398L;

	public MedidorException(Long clienteId, String string) {
		super(MessageFormat.format("Cannot find Medidor {0}/Cliente {1}", string, clienteId));
	}

}
