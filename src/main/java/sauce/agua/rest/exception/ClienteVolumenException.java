package sauce.agua.rest.exception;

public class ClienteVolumenException extends RuntimeException {

    public ClienteVolumenException(Long clienteId, Integer periodoId) {
        super("No existe el volumen para el cliente con id: " + clienteId + " y periodo: " + periodoId);
    }

}
