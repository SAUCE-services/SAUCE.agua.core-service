package sauce.agua.rest.hexagonal.operador.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.operador.domain.model.Operador;
import sauce.agua.rest.hexagonal.operador.domain.ports.out.OperadorRepository;
import sauce.agua.rest.hexagonal.operador.infrastructure.persistence.entity.OperadorEntity;
import sauce.agua.rest.hexagonal.operador.infrastructure.persistence.mapper.OperadorMapper;
import sauce.agua.rest.hexagonal.operador.infrastructure.persistence.repository.JpaOperadorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaOperadorRepositoryAdapter implements OperadorRepository {

    private final JpaOperadorRepository jpaOperadorRepository;
    private final OperadorMapper operadorMapper;

    @Override
    public List<Operador> findAll() {
        return jpaOperadorRepository.findAll(Sort.by("operadorId").ascending()).stream()
                .map(operadorMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Operador> findByOperadorId(Integer operadorId) {
        return jpaOperadorRepository.findByOperadorId(operadorId)
                .map(operadorMapper::toDomainModel);
    }

    @Override
    public Operador save(Operador operador) {
        OperadorEntity entity = operadorMapper.toEntity(operador);
        OperadorEntity savedEntity = jpaOperadorRepository.save(entity);
        return operadorMapper.toDomainModel(savedEntity);
    }

    @Override
    public void deleteByOperadorId(Integer operadorId) {
        jpaOperadorRepository.deleteByOperadorId(operadorId);
    }

    @Override
    public Optional<Operador> findTopByOrderByOperadorIdDesc() {
        return jpaOperadorRepository.findTopByOrderByOperadorIdDesc()
                .map(operadorMapper::toDomainModel);
    }
}
