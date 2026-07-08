package sauce.agua.rest.hexagonal.medidor.domain.model;

import java.time.OffsetDateTime;
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
public class Medidor {

    private Long uniqueId;
    private String medidorId;

    @Builder.Default
    private OffsetDateTime fechaAlta = OffsetDateTime.now();

    private Long clienteId;
    private OffsetDateTime fechaColocacion;
    private OffsetDateTime fechaRetiro;
    private Integer motivoRetiro;

    @Builder.Default
    private Long estadoInicio = 0L;

}
