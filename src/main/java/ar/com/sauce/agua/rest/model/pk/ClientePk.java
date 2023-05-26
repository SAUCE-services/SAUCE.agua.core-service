/**
 * 
 */
package ar.com.sauce.agua.rest.model.pk;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -203506042063823314L;

	private Long clienteId;
	private OffsetDateTime fechaAlta;
}
