package microfood.tickets.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import microfood.tickets.dtos.ExceptionDTO;
import microfood.tickets.exceptions.BadRequestException;
import microfood.tickets.exceptions.NotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionDTO> handleNotFound(Exception e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionDTO> handleBadRequest(Exception e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDTO> handleInternalError(Exception e) {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
