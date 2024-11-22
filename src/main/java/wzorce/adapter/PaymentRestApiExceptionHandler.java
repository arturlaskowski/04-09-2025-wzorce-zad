package wzorce.adapter;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class PaymentRestApiExceptionHandler {

    @ExceptionHandler(value = UnsupportedCurrencyException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UnsupportedCurrencyException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }
}
