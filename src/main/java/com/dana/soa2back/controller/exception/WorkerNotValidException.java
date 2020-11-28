package com.dana.soa2back.controller.exception;

public class WorkerNotValidException extends IllegalArgumentException {
    public WorkerNotValidException() {
        super("Worker's constraints are:<br>\n" +
                "coordinateX not null<br>\n" +
                "coordinateY < 444<br>\n" +
                "name not empty string<br>\n" +
                "salary may be null or >0<br>\n" +
                "organization's annualTurnover > 0<br>\n" +
                "organization's employeesCount >0\n");
    }
}
