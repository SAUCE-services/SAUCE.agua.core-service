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
public class DetallePk implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6128833043457080397L;

	private Integer prefijoId; 
	private Long facturaId;
	private Integer rubroId;
}
