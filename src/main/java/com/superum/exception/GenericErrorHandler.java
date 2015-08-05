package com.superum.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GenericErrorHandler {

    @ExceptionHandler
    void handleInvalidCustomerException(DatabaseException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database couldn't return value; " + e.getMessage());
    }

    @ExceptionHandler
    void handleDatabaseDuplicationException(DataAccessException e, HttpServletResponse response) throws Exception {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred");
    }

}
