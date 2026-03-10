package de.netschach.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.netschach.chess2.IllegalMoveException;
import de.netschach.security.IllegalHostException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(IllegalMoveException.class)
    public ResponseEntity<Error> handleIllegalMoveException(IllegalMoveException e) {
        log.error("{}", e.getMessage(), e);
        return new Error(e, HttpStatus.UNPROCESSABLE_ENTITY).toEntity();
    }

    @ExceptionHandler(IllegalHostException.class)
    public ResponseEntity<Error> handleIllegalHostException(IllegalHostException e) {
        log.error("{}", e.getMessage(), e);
        return new Error(e, HttpStatus.FORBIDDEN).toEntity();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception e) {
        log.error("{}", e.getMessage(), e);
        return new Error(e, HttpStatus.INTERNAL_SERVER_ERROR).toEntity();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("{}", e.getMessage(), e);
        return new Error(e, HttpStatus.UNPROCESSABLE_ENTITY).toEntity();
    }
}

@Getter
class Error {
    private String exception;
    private String message;
    @JsonIgnore
    private HttpStatus status;

    Error(Throwable t, HttpStatus status) {
        this.exception = t.getClass().getSimpleName();
        this.message = t.getMessage();
        this.status = status;
    }

    ResponseEntity<Error> toEntity() {
        return ResponseEntity.status(status).body(this);

    }

}