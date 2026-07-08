package sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.posicionIva.domain.model.PosicionIva;
import sauce.agua.rest.hexagonal.posicionIva.domain.ports.out.PosicionIvaRepository;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.entity.PosicionIvaEntity;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.mapper.PosicionIvaMapper;
import sauce.agua.rest.hexagonal.posicionIva.infrastructure.persistence.repository.JpaPosicionIvaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaPosicionIvaRepositoryAdapter implements PosicionIvaRepository {

    private final JpaPosicionIvaRepository jpaPosicionIvaRepository;
    private final PosicionIvaMapper posicionIvaMapper;

    @Override
    public List<PosicionIva> findAll() {
        return jpaPosicionIvaRepository.findAll(Sort.by("posicionId").ascending()).stream()
                .map(posicionIvaMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PosicionIva> findByPosicionId(Integer posicionId) {
        return jpaPosicionIvaRepository.findByPosicionId(posicionId)
                .map(posicionIvaMapper::toDomainModel);
    }

    @Override
    public PosicionIva save(PosicionIva posicionIva) {
        PosicionIvaEntity entity = posicionIvaMapper.toEntity(posicionIva);
        PosicionIvaEntity savedEntity = jpaPosicionIvaRepository.save(entity);
        return posicionIvaMapper.toDomainModel(savedEntity);
    }

    @Override
    public void deleteByPosicionId(Integer posicionId) {
        jpaPosicionIvaRepository.deleteByPosicionId(posicionId);
    }
}
