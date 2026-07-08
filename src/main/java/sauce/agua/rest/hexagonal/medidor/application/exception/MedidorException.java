package sauce.agua.rest.hexagonal.medidor.application.exception;

import java.text.MessageFormat;

public class MedidorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MedidorException(Long clienteId, String string) {
        super(MessageFormat.format("Cannot find Medidor {0}/Cliente {1}", string, clienteId));
    }
}
