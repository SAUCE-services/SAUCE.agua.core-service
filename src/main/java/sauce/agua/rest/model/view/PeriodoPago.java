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
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import sauce.agua.rest.model.view.pk.PeriodoPagoPk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_periodo_pago")
@Immutable
@IdClass(value = PeriodoPagoPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class PeriodoPago implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6050386162293107412L;

	@Id
	@Column(name = "periodo_id")
	private Integer periodoId;

	@Id
	@Column(name = "fecha_pago")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechapago;

	@Column(name = "descripcion")
	@NotNull
	@Size(max = 30)
	private String descripcion = "";
	
	@Column(name = "fecha_inicio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechainicio;

	@Column(name = "fecha_fin")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechafin;

	@Column(name = "fecha_primero")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaprimero;

	@Column(name = "fecha_segundo")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechasegundo;

	@Column(name = "tasa")
	@NotNull
	private BigDecimal tasa = new BigDecimal(0);

	@Column(name = "leyenda")
	@NotNull
	@Size(max = 300)
	private String leyenda = "";
	
	@Column(name = "liquidado")
	@NotNull
	private BigDecimal liquidado = new BigDecimal(0);

	@Column(name = "uid")
	@NotNull
	@Size(max = 20)
	private String uid = "";

}
