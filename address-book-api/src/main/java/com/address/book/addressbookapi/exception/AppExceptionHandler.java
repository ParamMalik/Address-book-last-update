package com.address.book.addressbookapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<String> handleInvalidArgument(ConstraintViolationException ex) {
//        HashMap<String, String> errorMap = new HashMap<>();
        ArrayList<String> errorList = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(err ->
                        errorList.add(err.getMessage()));
        return errorList;
    }


    @ResponseStatus
    @ExceptionHandler(ResourceAccessException.class)
    public List<String> handleConnectionTimeout(ResourceAccessException exception) {
        ArrayList<String> connList = new ArrayList<>();
        String custMessage = "Connection Is Not Established | Please try again later";
        connList.add(custMessage);
        return connList;
    }

}
