package sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sauce.agua.rest.model.Auditable;
import sauce.agua.rest.util.Jsonifyable;

@Getter
@Setter
@Entity
@Table(name = "clientedato")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDatoEntity extends Auditable implements Serializable, Jsonifyable {

    @Serial
    private static final long serialVersionUID = -4023845692833032325L;

    @Id
    private Long clienteId;

    private BigDecimal documento;
    private String email;
    private String fijo;
    private String celular;

}
