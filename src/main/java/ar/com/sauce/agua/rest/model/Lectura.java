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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "medidorId", "periodoId" }))
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Lectura extends Auditable implements Serializable {

	private static final long serialVersionUID = 3788446176234690168L;

	@Id
	@Column(name = "auto_id")
	private Long uniqueId;

	private String medidorId;
	private Integer periodoId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaLectura = OffsetDateTime.now();

	private Long estado = 0L;
	
}
