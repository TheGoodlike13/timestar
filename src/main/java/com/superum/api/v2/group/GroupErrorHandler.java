package com.superum.api.v2.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles how group specific exceptions are translated into HTTP responses
 */
@ControllerAdvice
public class GroupErrorHandler {

    @ExceptionHandler
    void handleInvalidGroupException(InvalidGroupException e, HttpServletResponse response) throws IOException {
        LOG.error("Invalid group format, check your JSON;", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid group format, check your JSON; " + e.getMessage());
    }

    @ExceptionHandler
    void handleGroupNotFoundException(GroupNotFoundException e, HttpServletResponse response) throws IOException {
        LOG.error("Cannot find the specified group;", e);
        response.sendError(HttpStatus.NOT_FOUND.value(), "Cannot find the specified group; " + e.getMessage());
    }

    @ExceptionHandler
    void handleUnsafeGroupDeleteException(UnsafeGroupDeleteException e, HttpServletResponse response) throws IOException {
        LOG.error("Cannot delete the specified group because it is still being used;", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Cannot delete the specified group because it is still being used; " + e.getMessage());
    }

    // PRIVATE

    private static final Logger LOG = LoggerFactory.getLogger(GroupErrorHandler.class);

}
