package sauce.agua.rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sauce.agua.rest.exception.ClienteVolumenException;
import sauce.agua.rest.model.ClienteVolumen;
import sauce.agua.rest.repository.ClienteVolumenRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteVolumenService {

    private final ClienteVolumenRepository repository;

    public ClienteVolumen findByUnique(Long clienteId, Integer periodoId) {
        var consumo = repository.findByClienteIdAndPeriodoId(clienteId, periodoId).orElseThrow(() -> new ClienteVolumenException(clienteId, periodoId));
        log.debug("Consumo -> " + consumo.jsonify());
        return consumo;
    }

}
