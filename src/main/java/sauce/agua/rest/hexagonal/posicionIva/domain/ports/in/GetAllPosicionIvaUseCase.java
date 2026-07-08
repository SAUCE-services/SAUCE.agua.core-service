package sauce.agua.rest.hexagonal.posicionIva.domain.ports.in;

import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;

import java.util.List;

public interface GetAllPosicionIvaUseCase {
    List<PosicionIva> getAllPosicionIva();
}
