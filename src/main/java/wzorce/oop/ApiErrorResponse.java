package wzorce.oop;

import java.time.Instant;

record ApiErrorResponse(String message, Instant timestamp) {

    public ApiErrorResponse(String message) {
        this(message, Instant.now());
    }
}