package com.dana.soa2back.service.manager;

import com.dana.soa2back.model.OrganizationType;
import com.dana.soa2back.model.Position;
import com.dana.soa2back.model.Status;
import com.dana.soa2back.model.Worker;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkerValidator {
    private static final ArrayList<String> WORKER_FIELDS = new ArrayList<>(Arrays.asList("name","coordinateX",
            "coordinateY","salary","startDate","endDate","position","status","annualTurnover","employeesCount",
            "organizationType","id","creationDate"));

    public static boolean validateWorker(Worker worker) {
        return worker.getCoordinates().getY() <= 444
                && worker.getCoordinates().getX() != null
                && !worker.getName().isEmpty()
                && (worker.getSalary() == null || worker.getSalary() > 0)
                && (worker.getOrganization() == null || worker.getOrganization().getAnnualTurnover() > 0
                && worker.getOrganization().getEmployeesCount() > 0);
    }

    public static boolean checkWorkerContainsForbiddenFields(Worker worker) {
        return checkWorkerContainsForbiddenFields(worker, false);
    }

    public static boolean checkWorkerContainsForbiddenFields(Worker worker, boolean idIsPresent) {
        return worker.getStartDate() != null
                || worker.getEndDate() != null
                || worker.getOrganization() != null
                || worker.getPosition() != null
                || worker.getStatus() != null
                || (idIsPresent && worker.getId() > 0);
    }

    private static boolean hasNoRedundantFields(List<String> fields) {
        return fields != null && fields.stream().noneMatch(x -> WORKER_FIELDS.stream().noneMatch(x::equals));
    }

    private static boolean checkFilterValuesForFilterSort(List<String> filterFields, List<String> filterValues) {
        try {
            for (int i = 0; i < filterFields.size(); ++i) {
                switch (filterFields.get(i)) {
                    case "position":
                        Position.getByTitle(filterValues.get(i)); //throws
                        break;
                    case "status":
                        Status.getByTitle(filterValues.get(i)); //throws
                        break;
                    case "organizationType":
                        if (OrganizationType.getByTitle(filterValues.get(i)) == null) return false;
                        break;
                    case "employeesCount":
                    case "annualTurnover":
                    case "id":
                        int inumber = Integer.parseInt(filterValues.get(i));
                        if (inumber < 0) return false;
                        break;
                    case "salary":
                        if (filterValues.get(i).equals("null")) break;
                    case "coordinateX":
                    case "coordinateY":
                        double dnumber = Double.parseDouble(filterValues.get(i));
                        if (filterFields.get(i).equals("coordinateY") && dnumber > 444) return false;
                        if (filterFields.get(i).equals("salary") && dnumber <= 0) return false;
                        break;
                    case "startDate":
                    case "endDate":
                        if (filterValues.get(i).equals("null")) break;
                        new SimpleDateFormat("dd-MM-yyyy").parse(filterValues.get(i));
                        break;
                    case "creationDate":
                        ZonedDateTime.parse(filterValues.get(i).replace(" ", "+"));
                        break;
                    case "name":
                        break;
                    default:
                        return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static boolean checkFilterForFilterSort(List<String> filterFields, List<String> filterValues) {
        return hasNoRedundantFields(filterFields) &&
                filterFields.size() == filterValues.size() &&
                checkFilterValuesForFilterSort(filterFields, filterValues);
    }

    public static boolean checkParametersForFilterSort(Integer pageSize, Integer pageNumber, List<String> filterFields,
                                                       List<String> filterValues, List<String> sortFields) {
        return (pageSize == null || pageSize > 0) && (pageNumber == null || pageNumber >= 0) &&
                (sortFields == null || hasNoRedundantFields(sortFields)) &&
                (filterFields == null && filterValues == null || checkFilterForFilterSort(filterFields, filterValues));
    }
}
