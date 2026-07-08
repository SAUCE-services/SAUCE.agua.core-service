package sauce.agua.rest.model;

import jakarta.persistence.*;
import lombok.*;
import sauce.agua.rest.util.Jsonifyable;

import java.io.Serializable;

@Data
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"clienteId", "periodoId"}))
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClienteVolumen extends Auditable implements Serializable, Jsonifyable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autoId;

    private Long clienteId;
    private Integer periodoId;
    private String medidorIdActual;
    private Long estadoActual;
    private String medidorIdAnterior;
    private Long estadoAnterior;
    private Long consumido;

}
