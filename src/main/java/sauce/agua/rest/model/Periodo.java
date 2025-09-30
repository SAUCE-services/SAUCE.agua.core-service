/**
 * 
 */
package sauce.agua.rest.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sauce.agua.rest.util.Jsonifier;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Periodo extends Auditable implements Serializable {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 8398649671543020277L;

	@Id
	private Integer periodoId;
	
	private String descripcion = "";
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaInicio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaFin;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaPrimero;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaSegundo;

	private BigDecimal tasa = BigDecimal.ZERO;
	private String leyenda = "";
	private BigDecimal liquidado = BigDecimal.ZERO;
	private String uid = "";

    public String jsonify() {
        return Jsonifier.builder(this).build();
    }
}
