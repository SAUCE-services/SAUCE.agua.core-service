/**
 * 
 */
package ar.com.sauce.agua.rest.model.pk;

import java.io.Serializable;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class NovedadPk implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7707974459635366030L;

	private Long clienteId;
	private Integer periodoId; 
	private Integer rubroId;
}
