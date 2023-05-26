/**
 * 
 */
package ar.com.sauce.agua.rest.exception;

/**
 * @author daniel
 *
 */
public class PeriodoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7110246459698948760L;

	public PeriodoNotFoundException() {
		super("Cannot find Periodo");
	}
	
	public PeriodoNotFoundException(Integer periodoId) {
		super("Cannot find Periodo " + periodoId);
	}

}
