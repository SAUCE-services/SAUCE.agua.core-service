package sauce.agua.rest.hexagonal.operador.application.exception;

public class OperadorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperadorException() {
        super("Cannot find Operador");
    }

    public OperadorException(Integer operadorId) {
        super("Cannot find Operador " + operadorId);
    }

}
