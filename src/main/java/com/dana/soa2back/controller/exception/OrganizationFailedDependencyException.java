package com.dana.soa2back.controller.exception;

public class OrganizationFailedDependencyException extends RuntimeException {
    public OrganizationFailedDependencyException() {
        super("Could not fire unemployed worker");
    }
}
