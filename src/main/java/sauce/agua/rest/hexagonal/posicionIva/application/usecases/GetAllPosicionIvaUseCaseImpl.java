package sauce.agua.rest.hexagonal.posicionIva.application.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.in.GetAllPosicionIvaUseCase;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.out.PosicionIvaRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllPosicionIvaUseCaseImpl implements GetAllPosicionIvaUseCase {

    private final PosicionIvaRepository posicionIvaRepository;

    @Override
    public List<PosicionIva> getAllPosicionIva() {
        return posicionIvaRepository.findAll();
    }
}
