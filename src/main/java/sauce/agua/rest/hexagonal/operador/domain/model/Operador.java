package sauce.agua.rest.hexagonal.operador.domain.model;

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
public class Operador {

    private Integer operadorId;

    @Builder.Default
    private String razonSocial = "";
    @Builder.Default
    private String calle = "";
    @Builder.Default
    private String puerta = "";
    @Builder.Default
    private String piso = "";
    @Builder.Default
    private String dpto = "";

    @Builder.Default
    private Integer codigoPostal = 0;
    @Builder.Default
    private String localidad = "";
    @Builder.Default
    private String provincia = "";
    @Builder.Default
    private String telefono = "";
    @Builder.Default
    private String cuit = "";
    @Builder.Default
    private String ingresosBrutos = "";

    @Builder.Default
    private Integer situacionIva = 0;
    @Builder.Default
    private String numeroEpas = "";
    private LocalDate fechaInicio;

    @Builder.Default
    private Integer servicio = 0;
    @Builder.Default
    private Integer prefijoId = 0;
    @Builder.Default
    private Integer facturaId = 0;
    @Builder.Default
    private Integer periodoFactura = 0;
    @Builder.Default
    private String resolucion = "";
    @Builder.Default
    private String personeria = "";

    @Builder.Default
    private Integer reciboSerie = 0;
    @Builder.Default
    private Integer recibo = 0;
    @Builder.Default
    private Integer ncreditoSerie = 0;
    @Builder.Default
    private Integer ncredito = 0;
    private String cai;
    private LocalDate caiVencimiento;

    @Builder.Default
    private Boolean preimpreso = false;
    @Builder.Default
    private String uid = "";

}
