package sauce.agua.rest.hexagonal.posicionIva.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.in.GetPosicionIvaByIdUseCase;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.out.PosicionIvaRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPosicionIvaByIdUseCaseImpl implements GetPosicionIvaByIdUseCase {

    private final PosicionIvaRepository posicionIvaRepository;

    @Override
    public Optional<PosicionIva> getPosicionIvaById(Integer id) {
        return posicionIvaRepository.findByPosicionId(id);
    }
}
