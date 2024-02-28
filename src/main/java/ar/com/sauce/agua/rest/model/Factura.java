/**
 * 
 */
package ar.com.sauce.agua.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.sauce.agua.rest.model.pk.FacturaPk;
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
@EqualsAndHashCode(callSuper = false)
@IdClass(value = FacturaPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class Factura extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3959463435349429544L;

	@Id
	private Integer prefijoId;

	@Id
	private Long facturaId;

	@Column(name = "auto_id")
	private Long uniqueId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fecha;

	private Long clienteId;
	private Integer periodoId;
	private Integer situacionIva = 0;
	private BigDecimal tasa = BigDecimal.ZERO;
	private BigDecimal descuento = BigDecimal.ZERO;
	private Byte pagada = 0;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaPago;

	private Integer tipoId = 0;
	private Byte anulada = 0;
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal interes = BigDecimal.ZERO;
	private String letras = "";
	private Integer prefijoIdInteres = 0;
	private Long facturaIdInteres = 0L;
	private BigDecimal ivaCf = BigDecimal.ZERO;
	private BigDecimal ivaRi = BigDecimal.ZERO;
	private BigDecimal ivaRn = BigDecimal.ZERO;
	private Integer periodoIdFin;
	private Byte cancelada = 0;
	private Integer planIdCancela;
	private String pfCodigo;
	private String pfBarras;
	private Long cajamovimientoId;
	private String uid = "";

	@OneToOne
	@JoinColumn(name = "periodoId", insertable = false, updatable = false)
	private Periodo periodo;

}
