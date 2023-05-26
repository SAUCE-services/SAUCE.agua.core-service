/**
 * 
 */
package ar.com.sauce.agua.rest.model.pk;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class NotificacionPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8118516822471074562L;

	private Long clienteId;
	private OffsetDateTime fecha;
}
