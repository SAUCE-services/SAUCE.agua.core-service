/**
 * 
 */
package ar.com.sauce.agua.rest.model;

import java.io.Serializable;
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

import ar.com.sauce.agua.rest.model.pk.NotificacionPk;
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
@Table(name = "notificacion")
@IdClass(NotificacionPk.class)
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1061970816665658855L;

	@Id
	private Long clienteId;

	@Id
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fecha;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificacionId;
	
	private Integer tiponotificacionId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime vencimiento;

	private Long estado = 0L;

}
