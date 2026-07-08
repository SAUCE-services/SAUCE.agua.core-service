package sauce.agua.rest.hexagonal.tipoNotificacion.application.exception;

public class TipoNotificacionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TipoNotificacionException() {
        super("Cannot find TipoNotificacion");
    }

    public TipoNotificacionException(Integer tiponotificacionId) {
        super("Cannot find TipoNotificacion " + tiponotificacionId);
    }

}
