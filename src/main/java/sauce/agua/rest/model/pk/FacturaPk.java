/**
 * 
 */
package sauce.agua.rest.model.pk;

import java.io.Serializable;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class FacturaPk implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -544886236818221163L;

	private Integer prefijoId;
	private Long facturaId;
}
