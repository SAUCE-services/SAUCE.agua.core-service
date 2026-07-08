package sauce.agua.rest.hexagonal.periodo.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {
    private Integer periodoId;
    
    @Builder.Default
    private String descripcion = "";
    
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;
    private OffsetDateTime fechaPrimero;
    private OffsetDateTime fechaSegundo;
    
    @Builder.Default
    private BigDecimal tasa = BigDecimal.ZERO;
    
    @Builder.Default
    private String leyenda = "";
    
    @Builder.Default
    private BigDecimal liquidado = BigDecimal.ZERO;
    
    @Builder.Default
    private String uid = "";
}
