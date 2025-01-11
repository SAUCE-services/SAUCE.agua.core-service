/**
 * 
 */
package sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class PeriodoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7110246459698948760L;

	public PeriodoException() {
		super("Cannot find Periodo");
	}
	
	public PeriodoException(Integer periodoId) {
		super("Cannot find Periodo " + periodoId);
	}

}
