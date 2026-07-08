package sauce.agua.rest.util;

public interface Jsonifyable {

    default String jsonify() {
        return Jsonifier.builder(this).build();
    }

}
