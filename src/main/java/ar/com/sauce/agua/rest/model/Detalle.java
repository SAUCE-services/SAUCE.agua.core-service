/**
 * 
 */
package ar.com.sauce.agua.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import ar.com.sauce.agua.rest.model.pk.DetallePk;
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
@Table
@IdClass(DetallePk.class)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Detalle extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7363171736163383427L;

	@Id
	private Integer prefijoId;

	@Id
	private Long facturaId;

	@Id
	private Integer rubroId;

	private String concepto = "";
	private BigDecimal cantidad = BigDecimal.ZERO;
	private BigDecimal precioUnitario = BigDecimal.ZERO;
	private Byte iva = 0;

	@Column(name = "auto_id")
	private Long detalleId;

}
