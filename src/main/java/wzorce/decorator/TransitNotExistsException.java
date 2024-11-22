package wzorce.decorator;

import java.util.UUID;

class TransitNotExistsException extends RuntimeException {

    public TransitNotExistsException(UUID transitId) {
        super("Transit " + transitId + " does not exist");
    }
}
