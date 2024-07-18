/**
 * 
 */
package sauce.agua.rest.model.view;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import sauce.agua.rest.model.Auditable;
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
@Table(name = "vw_socios_activos_con_cuota_fija")
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SocioActivoConCuotaFija extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5821607296954434370L;

	@Id
	private Long clienteId;
	
	@Column(name = "auto_id")
	private Long uniqueId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaAlta;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaBaja;

	private String apellido = "";
	private String nombre = "";
	private String numeroSocio = null;
	private String inmuebleCalle = "";
	private String inmueblePuerta = "";
	private String inmueblePiso = "";
	private String inmuebleDpto = "";
	private String inmuebleLocalidad = "";
	private String inmuebleProvincia = "";
	private Integer inmuebleCodpostal = 0;
	private String fiscalCalle = "";
	private String fiscalPuerta = "";
	private String fiscalPiso = "";
	private String fiscalDpto = "";
	private String fiscalLocalidad = "";
	private String fiscalProvincia = "";
	private Integer fiscalCodpostal = 0;
	private String cuit = "";
	private Integer situacionIva = 0;
	private String nombreCategoria = "";
	private Integer categoria = 0;
	private Integer servicio = 0;
	private Integer cobro = 0;
	private Integer zona = 0;
	private Integer ruta = 0;
	private Integer orden = 0;
	private Byte cortado = 0;
	private Integer estadoId = 0;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaNacimiento;

	private Integer categoriasocioId = 0;
	private Integer destinoId = 0;
	private String uid = "";

}
