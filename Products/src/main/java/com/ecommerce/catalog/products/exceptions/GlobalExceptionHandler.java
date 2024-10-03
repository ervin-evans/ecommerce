package com.ecommerce.catalog.products.exceptions;

import com.ecommerce.catalog.products.responses.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.zip.DataFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Message> handleInvalildInput(HttpMessageNotReadableException e) {
        MessageType messageType = MessageType.WARNING;
        Message message = Message.builder()
                .message("No hay informacion en el body")
                .type(messageType)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException e) {
        logger.warn("Hay errores en la validacion de la informacion");
        List<String> validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(validationErrors);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public  ResponseEntity<Message>handleProductNotFoundException(ProductNotFoundException e){
        logger.warn(e.getMessage());
        Message message = Message.builder()
                .message(e.getMessage())
                .type(MessageType.WARNING)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

    }

    @ExceptionHandler(DataFormatException.class)
    public ResponseEntity<Message> handleDataAccessException(DataAccessException e){
        logger.error("Ocurrio un error interno en el servidor");
        Message message = Message.builder()
                .message("Ocurrio un error interno en el servidor")
                .type(MessageType.ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

    }

}
