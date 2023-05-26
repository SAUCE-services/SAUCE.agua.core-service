/**
 * 
 */
package ar.com.sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class FacturaNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8663378095543271793L;

	public FacturaNotFoundException(Integer prefijoId, Long facturaId) {
		super("Cannot find Factura " + prefijoId + "/" + facturaId);
	}

}
