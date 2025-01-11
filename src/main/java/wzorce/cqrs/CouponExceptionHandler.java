package wzorce.cqrs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wzorce.cqrs.application.exception.CouponNotFoundException;
import wzorce.cqrs.domain.exception.CouponAlreadyUsedException;
import wzorce.cqrs.domain.exception.CouponNotActiveException;
import wzorce.cqrs.domain.exception.CouponNotValidException;

import java.time.Instant;

@ControllerAdvice
class CouponExceptionHandler {

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ApiError> handleCouponNotFoundException(CouponNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), Instant.now());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CouponAlreadyUsedException.class)
    public ResponseEntity<ApiError> handleCouponAlreadyUsedException(CouponAlreadyUsedException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), Instant.now());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CouponNotActiveException.class)
    public ResponseEntity<ApiError> handleCouponNotActiveException(CouponNotActiveException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), Instant.now());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CouponNotValidException.class)
    public ResponseEntity<ApiError> handleCouponNotValidException(CouponNotValidException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), Instant.now());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
