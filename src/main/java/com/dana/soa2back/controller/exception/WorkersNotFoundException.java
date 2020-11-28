package com.dana.soa2back.controller.exception;

public class WorkersNotFoundException extends RuntimeException {
    public WorkersNotFoundException() {
        super("Could not find workers");
    }
}
