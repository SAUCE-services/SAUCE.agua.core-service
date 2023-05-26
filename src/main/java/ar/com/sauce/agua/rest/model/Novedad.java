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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.sauce.agua.rest.model.pk.NovedadPk;
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
@Table(name = "novedad")
@IdClass(NovedadPk.class)
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Novedad extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2746276646106287130L;

	@Id
	private Long clienteId;

	@Id
	private Integer periodoId;

	@Id
	private Integer rubroId;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auto_id")
	private Long novedadId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fecha;

	private BigDecimal porcentaje = new BigDecimal(0);
	private BigDecimal cantidad = new BigDecimal(0);
	private BigDecimal importe = new BigDecimal(0);
	private Integer veces = 0;
	
	@Column(name = "veces_cobradas")
	private Integer vecescobradas = 0;
	private Byte indefinida = 0;
	
	@Column(name = "periodo_id_suspension")
	private Integer periodoIdsuspension;

}
