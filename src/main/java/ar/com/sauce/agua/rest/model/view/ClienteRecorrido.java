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
@Table(name = "vw_cliente_recorrido")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRecorrido extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2941470742006957463L;

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
	@NotNull
	private Integer inmueblecodpostal = 0;

	@Column(name = "fiscal_calle")
	@NotNull
	@Size(max = 50)
	private String fiscalcalle = "";

	@Column(name = "fiscal_puerta")
	@NotNull
	@Size(max = 10)
	private String fiscalpuerta = "";

	@Column(name = "fiscal_piso")
	@NotNull
	@Size(max = 10)
	private String fiscalpiso = "";

	@Column(name = "fiscal_dpto")
	@NotNull
	@Size(max = 10)
	private String fiscaldpto = "";

	@Column(name = "fiscal_localidad")
	@NotNull
	@Size(max = 50)
	private String fiscallocalidad = "";

	@Column(name = "fiscal_provincia")
	@NotNull
	@Size(max = 50)
	private String fiscalprovincia = "";

	@Column(name = "fiscal_codpostal")
	@NotNull
	private Integer fiscalcodpostal = 0;

	@Column(name = "cuit")
	@NotNull
	@Size(max = 11)
	private String cuit = "";

	@Column(name = "situacion_iva")
	@NotNull
	private Integer situacioniva = 0;

	@Column(name = "nombre_categoria")
	@NotNull
	@Size(max = 50)
	private String nombrecategoria = "";

	@Column(name = "categoria")
	@NotNull
	private Integer categoria = 0;

	@Column(name = "servicio")
	@NotNull
	private Integer servicio = 0;

	@Column(name = "cobro")
	@NotNull
	private Integer cobro = 0;

	@Column(name = "zona")
	@NotNull
	private Integer zona = 0;

	@Column(name = "ruta")
	@NotNull
	private Integer ruta = 0;

	@Column(name = "orden")
	@NotNull
	private Integer orden = 0;

	@Column(name = "cortado")
	@NotNull
	private Byte cortado = 0;

	@Column(name = "estado_id")
	@NotNull
	private Integer estadoId = 0;

	@Column(name = "fecha_nacimiento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechanacimiento;

	@Column(name = "categoriasocio_id")
	@NotNull
	private Integer categoriasocioId = 0;

	@Column(name = "destino_id")
	@NotNull
	private Integer destinoId = 0;

	@Column(name = "uid")
	@NotNull
	@Size(max = 20)
	private String uid = "";

	@Column(name = "recorrido")
	private Long recorrido;

}
