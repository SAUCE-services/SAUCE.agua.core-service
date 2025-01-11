/**
 * 
 */
package sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class FacturaException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8663378095543271793L;

	public FacturaException(Integer prefijoId, Long facturaId) {
		super("Cannot find Factura " + prefijoId + "/" + facturaId);
	}

}
