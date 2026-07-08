package sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
@Table(name = "medidor", uniqueConstraints = @UniqueConstraint(columnNames = {"medidorId", "fechaAlta", "clienteId"}))
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MedidorEntity extends Auditable implements Serializable, Jsonifyable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "auto_id")
    private Long uniqueId;

    private String medidorId;

    private OffsetDateTime fechaAlta;

    private Long clienteId;

    private OffsetDateTime fechaColocacion;

    private OffsetDateTime fechaRetiro;

    private Integer motivoRetiro;

    private Long estadoInicio;
}
