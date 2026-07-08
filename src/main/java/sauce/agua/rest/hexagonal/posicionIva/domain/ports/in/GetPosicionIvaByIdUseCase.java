package sauce.agua.rest.hexagonal.posicionIva.domain.ports.in;

import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;

import java.util.Optional;

public interface GetPosicionIvaByIdUseCase {
    Optional<PosicionIva> getPosicionIvaById(Integer id);
}
