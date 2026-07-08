package sauce.agua.rest.hexagonal.posicionIva.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.in.UpdatePosicionIvaUseCase;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.out.PosicionIvaRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdatePosicionIvaUseCaseImpl implements UpdatePosicionIvaUseCase {

    private final PosicionIvaRepository posicionIvaRepository;

    @Override
    public Optional<PosicionIva> updatePosicionIva(Integer id, PosicionIva newPosicionIva) {
        return posicionIvaRepository.findByPosicionId(id).map(posicionIva -> {
            posicionIva.setNombre(newPosicionIva.getNombre());
            posicionIva.setIdPosicionIvaArca(newPosicionIva.getIdPosicionIvaArca());
            return posicionIvaRepository.save(posicionIva);
        });
    }
}
