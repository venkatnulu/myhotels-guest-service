package com.myhotels.guestservice.advice;

import com.myhotels.guestservice.exceptions.GuestCreditCardNotFoundException;
import com.myhotels.guestservice.exceptions.GuestNotFoundException;
import com.myhotels.guestservice.exceptions.GuestStayInfoNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGuestProfileNotFoundException(GuestNotFoundException ex) {
        return Map.of("errorMessage", ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGuestStayInfoNotFoundException(GuestStayInfoNotFoundException ex) {
        return Map.of("errorMessage", ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleGuestCreditCardNotFoundException(GuestCreditCardNotFoundException ex) {
        return Map.of("errorMessage", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errMap.put(err.getField(), err.getDefaultMessage());
        });
        return errMap;
    }
}
