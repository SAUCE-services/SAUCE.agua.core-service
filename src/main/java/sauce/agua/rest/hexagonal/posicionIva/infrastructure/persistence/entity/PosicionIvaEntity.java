package sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.entity;

import java.io.Serial;
import java.io.Serializable;

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
@Table(name = "posicion_iva")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PosicionIvaEntity extends Auditable implements Serializable, Jsonifyable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer posicionId;

    private String nombre = "";
    private Integer idPosicionIvaArca;

}
