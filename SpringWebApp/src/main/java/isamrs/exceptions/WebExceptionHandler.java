package isamrs.exceptions;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;;

/*
 * Klasa koja sluzi za globalno hendlovanje izuzetaka
 */
@RestControllerAdvice
public class WebExceptionHandler{
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<String> respondTo404(isamrs.exceptions.NotFoundException nfe){
        return new ResponseEntity<>(nfe.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({LekarZauzetException.class})
    public ResponseEntity<String> respondToLekarZauzet(LekarZauzetException nfe){
        return new ResponseEntity<>(nfe.toString(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({SalaZauzetaException.class})
    public ResponseEntity<String> respondToLekarZauzet(SalaZauzetaException nfe){
        return new ResponseEntity<>(nfe.toString(), HttpStatus.BAD_REQUEST);
    }
}
