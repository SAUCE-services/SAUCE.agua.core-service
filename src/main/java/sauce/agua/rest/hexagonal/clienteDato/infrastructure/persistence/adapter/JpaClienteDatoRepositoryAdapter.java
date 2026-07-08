package sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.clienteDato.domain.model.ClienteDato;
import sauce.agua.rest.hexagonal.clienteDato.domain.ports.out.ClienteDatoRepository;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.entity.ClienteDatoEntity;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.mapper.ClienteDatoMapper;
import sauce.agua.rest.hexagonal.clienteDato.infrastructure.persistence.repository.JpaClienteDatoRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaClienteDatoRepositoryAdapter implements ClienteDatoRepository {

    private final JpaClienteDatoRepository jpaClienteDatoRepository;
    private final ClienteDatoMapper clienteDatoMapper;

    @Override
    public Optional<ClienteDato> findByClienteId(Long clienteId) {
        return jpaClienteDatoRepository.findByClienteId(clienteId)
                .map(clienteDatoMapper::toDomainModel);
    }

    @Override
    public ClienteDato save(ClienteDato clienteDato) {
        ClienteDatoEntity entity = clienteDatoMapper.toEntity(clienteDato);
        ClienteDatoEntity savedEntity = jpaClienteDatoRepository.save(entity);
        return clienteDatoMapper.toDomainModel(savedEntity);
    }
}
