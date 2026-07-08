package sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.medidor.domain.model.Medidor;
import sauce.agua.rest.hexagonal.medidor.domain.ports.out.MedidorRepository;
import sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.mapper.MedidorMapper;
import sauce.agua.rest.hexagonal.medidor.infrastructure.persistence.repository.JpaMedidorRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaMedidorRepositoryAdapter implements MedidorRepository {

    private final JpaMedidorRepository jpaMedidorRepository;
    private final MedidorMapper medidorMapper;

    @Override
    public Optional<Medidor> findTopByClienteId(Long clienteId, Sort sort) {
        return jpaMedidorRepository.findTopByClienteId(clienteId, sort)
                .map(medidorMapper::toDomainModel);
    }

    @Override
    public Optional<Medidor> findTopByClienteIdAndFechaRetiroIsNull(Long clienteId, Sort sort) {
        return jpaMedidorRepository.findTopByClienteIdAndFechaRetiroIsNull(clienteId, sort)
                .map(medidorMapper::toDomainModel);
    }
}
