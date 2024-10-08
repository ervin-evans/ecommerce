package com.ecommerce.users.exceptions;

import com.ecommerce.users.response.Message;
import com.ecommerce.users.response.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.zip.DataFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /******************************************************************************************************************
     *                                      HANDLER FOR DATA VALIDATION
     ******************************************************************************************************************/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException e) {
        logger.warn("Hay errores en la validacion de la informacion del usuario");
        List<String> validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(validationErrors);
    }
    /******************************************************************************************************************
     *                                      HANDLER FOR USER NOT FOUND EXCEPTION
     ******************************************************************************************************************/
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(UserNotFoundException e) {
        logger.warn(e.getMessage());
        var message = Message.builder()
                .message(e.getMessage())
                .type(MessageType.INFO)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    /******************************************************************************************************************
     *                                           DATA ACCESS EXCEPTION
     ******************************************************************************************************************/
    @ExceptionHandler(DataFormatException.class)
    public ResponseEntity<Message> handleDataAccessException(DataAccessException e){
        logger.error("Ocurrio un error interno en el servidor debido a: " + e.getMessage());
        Message message = Message.builder()
                .message("Ocurrio un error interno en el servidor, contacte al administrador")
                .type(MessageType.ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


}
