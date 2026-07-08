package sauce.agua.rest.hexagonal.posicionIva.domain.ports.out;

import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;

import java.util.List;
import java.util.Optional;

public interface PosicionIvaRepository {

    List<PosicionIva> findAll();

    Optional<PosicionIva> findByPosicionId(Integer posicionId);

    PosicionIva save(PosicionIva posicionIva);

    void deleteByPosicionId(Integer posicionId);

}
