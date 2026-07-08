package sauce.agua.rest.hexagonal.clienteDato.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDatoResponse {

    private Long clienteId;
    private BigDecimal documento;
    private String email;
    private String fijo;
    private String celular;

}
