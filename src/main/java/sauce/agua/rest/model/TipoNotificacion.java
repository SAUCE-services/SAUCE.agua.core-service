/**
 * 
 */
package sauce.agua.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
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
@Table(name = "tipo_notificacion")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TipoNotificacion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -8418687411977518173L;

	@Id
	private Integer tiponotificacionId;
	private String nombre = "";
	
	@Column(name = "valor_socio")
	private BigDecimal valorsocio = new BigDecimal(0);
	
	@Column(name = "valor_no_socio")
	private BigDecimal valornosocio = new BigDecimal(0);
	
}
