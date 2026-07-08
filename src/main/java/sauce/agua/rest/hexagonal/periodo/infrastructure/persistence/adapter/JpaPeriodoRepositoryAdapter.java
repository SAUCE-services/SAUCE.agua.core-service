package sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sauce.agua.rest.hexagonal.periodo.application.exception.PeriodoException;
import sauce.agua.rest.hexagonal.periodo.domain.model.Periodo;
import sauce.agua.rest.hexagonal.periodo.domain.ports.out.PeriodoRepository;
import sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.entity.PeriodoEntity;
import sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.mapper.PeriodoMapper;
import sauce.agua.rest.hexagonal.periodo.infrastructure.persistence.repository.JpaPeriodoRepository;
import sauce.agua.rest.model.view.PeriodoPago;
import sauce.agua.rest.repository.view.IPeriodoPagoRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaPeriodoRepositoryAdapter implements PeriodoRepository {

    private final JpaPeriodoRepository jpaPeriodoRepository;
    private final IPeriodoPagoRepository periodoPagoRepository;
    private final PeriodoMapper periodoMapper;

    @Override
    public List<Periodo> findAll() {
        return jpaPeriodoRepository.findAll(Sort.by("periodoId").descending()).stream()
                .map(periodoMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Periodo> findByPeriodoId(Integer periodoId) {
        return jpaPeriodoRepository.findByPeriodoId(periodoId)
                .map(periodoMapper::toDomainModel);
    }

    @Override
    public Periodo save(Periodo periodo) {
        PeriodoEntity entity = periodoMapper.toEntity(periodo);
        PeriodoEntity savedEntity = jpaPeriodoRepository.save(entity);
        return periodoMapper.toDomainModel(savedEntity);
    }

    @Override
    public void deleteByPeriodoId(Integer periodoId) {
        jpaPeriodoRepository.deleteByPeriodoId(periodoId);
    }

    @Override
    public Optional<Periodo> findTopByOrderByPeriodoIdDesc() {
        return jpaPeriodoRepository.findTopByOrderByPeriodoIdDesc()
                .map(periodoMapper::toDomainModel);
    }

    @Override
    public Optional<Periodo> findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(
            OffsetDateTime fechaLess, OffsetDateTime fechaGreater) {
        return jpaPeriodoRepository.findTopByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByPeriodoIdDesc(fechaLess, fechaGreater)
                .map(periodoMapper::toDomainModel);
    }

    @Override
    public Optional<Periodo> findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(OffsetDateTime today) {
        return jpaPeriodoRepository.findTopByFechaInicioLessThanEqualOrderByPeriodoIdDesc(today)
                .map(periodoMapper::toDomainModel);
    }

    @Override
    public List<Periodo> findAllRecaudadoByPeriodo(OffsetDateTime desde, OffsetDateTime hasta) {
        List<PeriodoPago> pagos = periodoPagoRepository.findAllByFechapagoBetween(
                desde,
                hasta,
                Sort.by("periodoId").ascending()
        );

        return pagos.stream()
                .map(PeriodoPago::getPeriodoId)
                .distinct()
                .map(periodoId -> jpaPeriodoRepository.findByPeriodoId(periodoId)
                        .orElseThrow(() -> new PeriodoException(periodoId)))
                .map(periodoMapper::toDomainModel)
                .collect(Collectors.toList());
    }
}
