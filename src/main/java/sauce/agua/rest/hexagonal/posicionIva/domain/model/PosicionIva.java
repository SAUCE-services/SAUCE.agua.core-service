package sauce.agua.rest.hexagonal.posicionIva.domain.model;

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
public class PosicionIva {

    private Integer posicionId;

    @Builder.Default
    private String nombre = "";

    private Integer idPosicionIvaArca;

}
