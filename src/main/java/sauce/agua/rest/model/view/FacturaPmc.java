/**
 * 
 */
package sauce.agua.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Immutable
@Table(name = "vw_factura_pmc")
public class FacturaPmc implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -421581368202166320L;

	@Id
	@Column(name = "auto_id")
	private Long uniqueId;
	
	private Long clienteId;
	private Integer prefijoId;
	private Long facturaId;
	private BigDecimal total;
	private Integer periodoId;

	@Column(name = "pf_codigo")
	private String pfcodigo;
	private String descripcion;

	@Column(name = "fecha_primero")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaprimero;

	@Column(name = "fecha_segundo")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechasegundo;

	private BigDecimal tasa;
	
}
