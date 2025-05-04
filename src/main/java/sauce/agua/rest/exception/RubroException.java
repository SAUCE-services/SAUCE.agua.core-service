package sauce.agua.rest.exception;

public class RubroException extends RuntimeException{

    public RubroException(Integer rubroId) {
        super("No existe el rubro con id: " + rubroId);
    }

}
