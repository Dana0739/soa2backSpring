package com.dana.soa2back.controller.exception;

public class IllegalWorkerArgumentsException extends IllegalArgumentException {
    public IllegalWorkerArgumentsException() {
        super("Workers cannot be created or replaced with these arguments:<br>\n" +
                "startDate<br>\n" +
                "endDate<br>\n" +
                "organization (and inner fields)<br>\n" +
                "position<br>\n" +
                "status<br>\n" +
                "id (in case of creation via POST)<br>\n<br>\n" +
                "Managing them is allowed through PATCH method.");
    }
}
