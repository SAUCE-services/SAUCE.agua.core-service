package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.web.dto;

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
public class TipoNotificacionResponse {

    private Integer tiponotificacionId;
    private String nombre;
    private BigDecimal valorsocio;
    private BigDecimal valornosocio;

}
