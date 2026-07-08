package sauce.agua.rest.hexagonal.posicionIva.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.in.DeletePosicionIvaUseCase;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.out.PosicionIvaRepository;

@Component
@RequiredArgsConstructor
public class DeletePosicionIvaUseCaseImpl implements DeletePosicionIvaUseCase {

    private final PosicionIvaRepository posicionIvaRepository;

    @Override
    public void deletePosicionIva(Integer id) {
        posicionIvaRepository.deleteByPosicionId(id);
    }
}
