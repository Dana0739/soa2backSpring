package com.dana.soa2back.controller.exception;

public class IllegalWorkerFilterSortPagingArgumentsException extends IllegalArgumentException {
    public IllegalWorkerFilterSortPagingArgumentsException() {
        super("Workers could be found using these arguments in an appropriate way:<br>\n" +
                "filterFields and filterValues - are lists with worker fields from list below and expected values<br>\n" +
                "sortFields - is list with worker fields from list below by which workers will be sorted<br>\n" +
                "pageSize - is a size shown page (>0)<br>\n" +
                "pageNumber - is a number of shown page (>=0)<br>\n<br>\n" +
                "list of fields: <br>\n<br>\n" +
                "\tname<br>\n" +
                "\tcoordinateX<br>\n" +
                "\tcoordinateY<br>\n" +
                "\tsalary<br>\n" +
                "\tstartDate<br>\n" +
                "\tendDate<br>\n" +
                "\tposition<br>\n" +
                "\tstatus<br>\n" +
                "\tannualTurnover<br>\n" +
                "\temployeesCount<br>\n" +
                "\torganizationType<br>\n" +
                "\tid<br>\n" +
                "\tcreationDate\n");
    }
}
