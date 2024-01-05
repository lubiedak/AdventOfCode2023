package advent.of.code.controller;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class TaskControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ExecutionControl.NotImplementedException.class})
    protected ResponseEntity<Object> handleConflict(
            ExecutionControl.InternalException ex, WebRequest request) {
        String bodyOfResponse = "Task not implemented";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED, request);
    }
}