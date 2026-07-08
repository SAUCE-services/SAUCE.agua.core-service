package sauce.agua.rest.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import sauce.agua.rest.util.Jsonifyable;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionDto implements Jsonifyable {

    @JsonProperty("cliente_id")
    private Long clienteId;

    @Builder.Default
    @JsonProperty("tipo_documento")
    private int tipoDocumento = 0;

    @Builder.Default
    private String documento = "0";

    @Builder.Default
    @JsonProperty("tipo_afip")
    private int tipoAfip = 0;

    @Builder.Default
    @JsonProperty("punto_venta")
    private int puntoVenta = 0;

    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal exento = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal neto = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal neto105 = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal iva = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal iva105 = BigDecimal.ZERO;
    @Builder.Default
    private String resultado = "";
    @Builder.Default
    private String cae = "";

    @Builder.Default
    @JsonProperty("vencimiento_cae")
    private String vencimientoCae = "";

    @Builder.Default
    @JsonProperty("numero_comprobante")
    private long numeroComprobante = 0;

    @JsonProperty("asociado_tipo_afip")
    private Integer asociadoTipoAfip;

    @JsonProperty("asociado_punto_venta")
    private Integer asociadoPuntoVenta;

    @JsonProperty("asociado_numero_comprobante")
    private Long asociadoNumeroComprobante;

    @JsonProperty("asociado_fecha_comprobante")
    private String asociadoFechaComprobante;

    @JsonProperty("id_condicion_iva")
    private Integer idCondicionIva;

}
