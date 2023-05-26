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
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.sauce.agua.rest.model.pk.RubroPk;
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
@Table(name = "rubro")
@IdClass(RubroPk.class)
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Rubro extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814175573718879049L;

	@Id
	private Integer rubroId;
	
	@Id
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fecha = OffsetDateTime.now();
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auto_id")
	private Long uniqueId;

	private Integer rangoId = 0;
	private String concepto = "";
	
	@Column(name = "precio_unitario")
	private BigDecimal preciounitario = new BigDecimal(0);
	
	private Byte iva = 0;
	private Byte comun = 0;
	
	@Column(name = "comun_socio")
	private Byte comunsocio = 0;
	private Integer cobro = 0;
	private Byte desconectado = 0;
	
}
