package com.dana.soa2back.controller;

import com.dana.soa2back.controller.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ResponseBody
    @ExceptionHandler(WorkerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String workerNotFoundHandler(WorkerNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OrganizationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String organizationNotFoundHandler(OrganizationNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WorkersNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String workersNotFoundHandler(WorkersNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OrganizationsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String organizationsNotFoundHandler(OrganizationsNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalWorkerArgumentsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String illegalWorkerArgumentsHandler(IllegalWorkerArgumentsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalWorkerFilterSortPagingArgumentsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String illegalWorkerArgumentsHandler(IllegalWorkerFilterSortPagingArgumentsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WorkerNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String workerNotValidExceptionHandler(WorkerNotValidException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OrganizationNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String organizationNotValidExceptionHandler(OrganizationNotValidException ex) {
        return ex.getMessage();
    }
}
