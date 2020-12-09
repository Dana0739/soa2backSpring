package com.dana.soa2back.controller.exception;

public class OrganizationNotValidException extends IllegalArgumentException {
    public OrganizationNotValidException() {
        super("Organization's constraints are:<br>\n" +
                "id not null (and > 0)<br>\n" +
                "type not null<br>\n" +
                "annualTurnover > 0<br>\n" +
                "employeesCount >0\n");
    }
}
