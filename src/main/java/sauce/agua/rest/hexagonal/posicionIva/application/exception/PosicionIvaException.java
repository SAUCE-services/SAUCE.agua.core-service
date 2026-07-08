package sauce.agua.rest.hexagonal.posicionIva.application.exception;

public class PosicionIvaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PosicionIvaException() {
        super("Cannot find PosicionIva");
    }

    public PosicionIvaException(Integer posicionId) {
        super("Cannot find PosicionIva " + posicionId);
    }

}
