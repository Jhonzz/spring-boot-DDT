package academy.devdojo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(NotFoundException e){
        var error = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), e.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
