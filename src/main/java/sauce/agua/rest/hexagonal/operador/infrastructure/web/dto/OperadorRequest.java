package sauce.agua.rest.hexagonal.operador.infrastructure.web.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperadorRequest {

    private String razonSocial;
    private String calle;
    private String puerta;
    private String piso;
    private String dpto;
    private Integer codigoPostal;
    private String localidad;
    private String provincia;
    private String telefono;
    private String cuit;
    private String ingresosBrutos;
    private Integer situacionIva;
    private String numeroEpas;
    private LocalDate fechaInicio;
    private Integer servicio;
    private Integer prefijoId;
    private Integer facturaId;
    private Integer periodoFactura;
    private String resolucion;
    private String personeria;
    private Integer reciboSerie;
    private Integer recibo;
    private Integer ncreditoSerie;
    private Integer ncredito;
    private String cai;
    private LocalDate caiVencimiento;
    private Boolean preimpreso;

}
