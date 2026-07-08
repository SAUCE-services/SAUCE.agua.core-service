package sauce.agua.rest.hexagonal.posicionIva.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.in.AddPosicionIvaUseCase;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.out.PosicionIvaRepository;

@Component
@RequiredArgsConstructor
public class AddPosicionIvaUseCaseImpl implements AddPosicionIvaUseCase {

    private final PosicionIvaRepository posicionIvaRepository;

    @Override
    public PosicionIva add(PosicionIva posicionIva) {
        return posicionIvaRepository.save(posicionIva);
    }
}
