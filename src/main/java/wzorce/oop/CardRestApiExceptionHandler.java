package wzorce.oop;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
class CardRestApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String aggregatedErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        var apiErrorResponse = new ApiErrorResponse(aggregatedErrors);
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CardOperationException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CardOperationException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CardNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CardNotFoundException ex) {
        var apiErrorResponse = new ApiErrorResponse(ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }
}
