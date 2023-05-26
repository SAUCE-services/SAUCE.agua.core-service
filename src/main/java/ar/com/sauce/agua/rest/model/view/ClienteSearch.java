/**
 * 
 */
package ar.com.sauce.agua.rest.model.view;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.com.sauce.agua.rest.model.Auditable;
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
@Immutable
@Table(name = "vw_cliente_search")
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteSearch extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4616622668625875046L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auto_id")
	private Long uniqueId;
	
	@Column(name = "cliente_id")
	private Long clienteId;
	
	@Column(name = "fecha_alta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaalta;

	@Column(name = "fecha_baja")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechabaja;
	
	@Column(name = "apellido")
	@NotNull
	@Size(max = 50)
	private String apellido = "";

	@Column(name = "nombre")
	@NotNull
	@Size(max = 50)
	private String nombre = "";

	@Column(name = "numero_socio")
	@Size(max = 10)
	private String numerosocio;

	@Column(name = "inmueble_calle")
	@NotNull
	@Size(max = 50)
	private String inmueblecalle = "";

	@Column(name = "inmueble_puerta")
	@NotNull
	@Size(max = 10)
	private String inmueblepuerta = "";

	@Column(name = "inmueble_piso")
	@NotNull
	@Size(max = 10)
	private String inmueblepiso = "";

	@Column(name = "inmueble_dpto")
	@NotNull
	@Size(max = 10)
	private String inmuebledpto = "";

	@Column(name = "inmueble_localidad")
	@NotNull
	@Size(max = 50)
	private String inmueblelocalidad = "";

	@Column(name = "inmueble_provincia")
	@NotNull
	@Size(max = 50)
	private String inmuebleprovincia = "";

	@Column(name = "inmueble_codpostal")
	private Integer inmueblecodpostal = 0;

	@Column(name = "fiscal_calle")
	private String fiscalcalle = "";

	@Column(name = "fiscal_puerta")
	private String fiscalpuerta = "";

	@Column(name = "fiscal_piso")
	private String fiscalpiso = "";

	@Column(name = "fiscal_dpto")
	private String fiscaldpto = "";

	@Column(name = "fiscal_localidad")
	private String fiscallocalidad = "";

	@Column(name = "fiscal_provincia")
	private String fiscalprovincia = "";

	@Column(name = "fiscal_codpostal")
	private Integer fiscalcodpostal = 0;

	private String cuit = "";

	@Column(name = "situacion_iva")
	private Integer situacioniva = 0;
	@Column(name = "nombre_categoria")
	private String nombrecategoria = "";
	private Integer categoria = 0;
	private Integer servicio = 0;
	private Integer cobro = 0;
	private Integer zona = 0;
	private Integer ruta = 0;
	private Integer orden = 0;
	private Byte cortado = 0;
	private Integer estadoId = 0;

	@Column(name = "fecha_nacimiento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechanacimiento;
	
	private Integer categoriasocioId = 0;
	private Integer destinoId = 0;
	private String uid = "";
	private String search;

}
