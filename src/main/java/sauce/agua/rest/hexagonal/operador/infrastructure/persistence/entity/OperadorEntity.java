package sauce.agua.rest.hexagonal.operador.infrastructure.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sauce.agua.rest.model.Auditable;
import sauce.agua.rest.util.Jsonifyable;

@Getter
@Setter
@Entity
@Table(name = "operador")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OperadorEntity extends Auditable implements Serializable, Jsonifyable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer operadorId;

    private String razonSocial = "";
    private String calle = "";
    private String puerta = "";
    private String piso = "";
    private String dpto = "";
    private Integer codigoPostal = 0;
    private String localidad = "";
    private String provincia = "";
    private String telefono = "";
    private String cuit = "";
    private String ingresosBrutos = "";
    private Integer situacionIva = 0;
    private String numeroEpas = "";
    private LocalDate fechaInicio;
    private Integer servicio = 0;
    private Integer prefijoId = 0;
    private Integer facturaId = 0;
    private Integer periodoFactura = 0;
    private String resolucion = "";
    private String personeria = "";
    private Integer reciboSerie = 0;
    private Integer recibo = 0;
    private Integer ncreditoSerie = 0;
    private Integer ncredito = 0;
    private String cai;
    private LocalDate caiVencimiento;
    private Boolean preimpreso = false;
    private String uid = "";
}
