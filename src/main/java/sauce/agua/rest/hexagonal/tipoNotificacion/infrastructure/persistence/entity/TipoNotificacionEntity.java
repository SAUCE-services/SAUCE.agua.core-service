package sauce.agua.rest.hexagonal.tipoNotificacion.infrastructure.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
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
@Table(name = "tipo_notificacion")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TipoNotificacionEntity extends Auditable implements Serializable, Jsonifyable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer tiponotificacionId;

    private String nombre = "";

    @Column(name = "valor_socio")
    private BigDecimal valorsocio = new BigDecimal(0);

    @Column(name = "valor_no_socio")
    private BigDecimal valornosocio = new BigDecimal(0);

}
