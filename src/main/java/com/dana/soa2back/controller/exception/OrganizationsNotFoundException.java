package com.dana.soa2back.controller.exception;

public class OrganizationsNotFoundException extends RuntimeException {
    public OrganizationsNotFoundException() {
        super("Could not find organizations");
    }
}
