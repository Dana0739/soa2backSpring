package com.dana.soa2back.controller.exception;

public class WorkerNotFoundException extends RuntimeException {
    public WorkerNotFoundException(Long id) {
        super("Could not find worker with id " + id);
    }
}
