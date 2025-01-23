/**
 * 
 */
package sauce.agua.rest.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "clientedato")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDato extends Auditable implements Serializable {

	@Serial
	private static final long serialVersionUID = -4023845692833032325L;

	@Id
	private Long clienteId;

	private BigDecimal documento;
	private String email;
	private String fijo;
	private String celular;

}
