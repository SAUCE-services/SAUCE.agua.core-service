package sauce.agua.rest.hexagonal.posicionIva.infrastructure.web.dto;

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
public class PosicionIvaRequest {

    private String nombre;
    private Integer idPosicionIvaArca;

}
