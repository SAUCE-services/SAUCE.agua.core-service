/**
 * 
 */
package ar.com.sauce.agua.rest.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "clienteId", "periodoId" }))
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Medicion extends Auditable implements Serializable {

	private static final long serialVersionUID = -3368786207729164695L;

	@Id
	@Column(name = "auto_id")
	private Long uniqueId;

	private Long clienteId;
	private Integer periodoId;
	private String medidorId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaLectura;

	private Long estado = 0L;

}
