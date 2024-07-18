/**
 * 
 */
package sauce.agua.rest.model.internal;

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
public class DatoConsumo implements Serializable {
	private static final long serialVersionUID = 9097837541421652728L;
	
	private OffsetDateTime fechaActual;
	private OffsetDateTime fechaAnterior;
	private Long estadoActual;
	private Long estadoAnterior;
	private Long consumo;

}
