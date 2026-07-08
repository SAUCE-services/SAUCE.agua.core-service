package sauce.agua.rest.hexagonal.posicionIva.domain.ports.in;

import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;

public interface AddPosicionIvaUseCase {
    PosicionIva add(PosicionIva posicionIva);
}
