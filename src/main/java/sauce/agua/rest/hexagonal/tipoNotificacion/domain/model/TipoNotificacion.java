package sauce.agua.rest.hexagonal.tipoNotificacion.domain.model;

import java.math.BigDecimal;
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
public class TipoNotificacion {

    private Integer tiponotificacionId;

    @Builder.Default
    private String nombre = "";

    @Builder.Default
    private BigDecimal valorsocio = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal valornosocio = BigDecimal.ZERO;

}
