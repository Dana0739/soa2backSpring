package com.dana.soa2back.service.manager;

import com.dana.soa2back.model.DTO.HRHireFireWorkerDTO;
import com.dana.soa2back.model.OrganizationType;
import com.dana.soa2back.model.Position;
import com.dana.soa2back.model.Status;
import com.dana.soa2back.model.Worker;
import com.dana.soa2back.service.repository.OrganizationRepository;
import com.dana.soa2back.service.repository.WorkerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

import static java.lang.Integer.min;

@Component
public class WorkerManager {

    private static final Map<String, String> WORKER_SORT_INNER_FIELDS = new HashMap<>();

    static {
        WORKER_SORT_INNER_FIELDS.put("coordinateX", "coordinates.x");
        WORKER_SORT_INNER_FIELDS.put("coordinateY", "coordinates.y");
        WORKER_SORT_INNER_FIELDS.put("annualTurnover", "organization.annualTurnover");
        WORKER_SORT_INNER_FIELDS.put("employeesCount", "organization.employeesCount");
        WORKER_SORT_INNER_FIELDS.put("organizationType", "organization.type");
    }

    private final WorkerRepository workerRepository;
    private final OrganizationRepository organizationRepository;

    public WorkerManager(WorkerRepository workerRepository, OrganizationRepository organizationRepository) {
        this.workerRepository = workerRepository;
        this.organizationRepository = organizationRepository;
    }

    public Optional<Worker> findById(Long id) {
        return workerRepository.findById(id);
    }

    public Optional<Worker> getMaxSalary() {
        return workerRepository.findTopByOrderBySalary();
    }

    public int getWorkersCountWithEqualSalary(Double salary) {
        return workerRepository.countAllBySalary(salary);
    }

    public List<Worker> getWorkersWithNameStartsWithPrefix(String prefix) {
        return workerRepository.findAllByNameStartsWith(prefix);
    }

    public Worker saveWorker(Worker newWorker) {
        return workerRepository.save(newWorker);
    }

    public boolean workerExistsById(Long id) {
        return workerRepository.existsById(id);
    }

    public boolean organizationExistsById(Long id) {
        return organizationRepository.existsById(id);
    }

    public Worker editWorker(HRHireFireWorkerDTO hrHireFireWorkerDTO, long id) {
        Worker worker = workerRepository.findById(id).get();
        if (hrHireFireWorkerDTO.getOrganizationId() != null)
            worker.setOrganization(organizationRepository.findById(hrHireFireWorkerDTO.getOrganizationId()).get());
        if (hrHireFireWorkerDTO.getPosition() != null)
            worker.setPosition(hrHireFireWorkerDTO.getPosition());
        if (hrHireFireWorkerDTO.getStatus() != null)
            worker.setStatus(hrHireFireWorkerDTO.getStatus());
        if (hrHireFireWorkerDTO.getStartDate() != null)
            worker.setStartDate(hrHireFireWorkerDTO.getStartDate());
        worker.setEndDate(hrHireFireWorkerDTO.getEndDate());
        return workerRepository.save(worker);
    }

    public void deleteWorkerById(long id) {
        workerRepository.deleteById(id);
    }


    public List<Worker> getAllWorkers(List<String> filterFields, List<String> filterValues, List<String> sortFields) {
        List<Worker> workers;
        if (sortFields != null && sortFields.size() > 0) {
            List<Sort.Order> sortOrders = makeSortOrders(sortFields);
            workers = workerRepository.findAll(Sort.by(sortOrders));
        } else {
            workers = workerRepository.findAll();
        }
        if (filterFields != null && filterFields.size() > 0) {
            workers = makeFilteredWorkersList(workers, filterFields, filterValues);
        }
        return workers;
    }

    public List<Worker> pageWorkersList(List<Worker> workers, Integer pageNumber, Integer pageSize) {
        if (pageNumber == null) pageNumber = 0;
        if (pageSize == null) pageSize = 0;
        return (pageSize == 0 || workers.size() == 0) ? workers
                : ((pageNumber * pageSize > workers.size()) ? new ArrayList<>()
                : new ArrayList<>(workers.subList(pageNumber * pageSize,
                min((pageNumber + 1) * pageSize, workers.size()))));
    }

    private static List<Sort.Order> makeSortOrders(List<String> sortFields) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String str : sortFields) {
            if (WORKER_SORT_INNER_FIELDS.containsKey(str)) str = WORKER_SORT_INNER_FIELDS.get(str);
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, str);
            orders.add(order);
        }
        return orders;
    }

    private static List<Worker> makeFilteredWorkersList(List<Worker> workers, List<String> filterFields,
                                                        List<String> filterValues) {
        if (!filterFields.isEmpty()) {
            if (filterFields.contains("id")) {
                workers.removeIf(worker ->
                        worker.getId() != Integer.parseInt(filterValues.get(filterFields.indexOf("id"))));
            }
            if (filterFields.contains("name")) {
                workers.removeIf(worker ->
                        !worker.getName().equals(filterValues.get(filterFields.indexOf("name"))));
            }
            if (filterFields.contains("coordinateX")) {
                workers.removeIf(worker -> worker.getCoordinates().getX() !=
                        Double.parseDouble(filterValues.get(filterFields.indexOf("coordinateX"))));
            }
            if (filterFields.contains("coordinateY")) {
                workers.removeIf(worker -> worker.getCoordinates().getY() !=
                        Double.parseDouble(filterValues.get(filterFields.indexOf("coordinateY"))));
            }
            if (filterFields.contains("creationDate")) {
                workers.removeIf(worker -> worker.getCreationDate() !=
                        ZonedDateTime.parse(filterValues.get(filterFields.indexOf("creationDate")).replace(" ", "+")));
            }
            if (filterFields.contains("salary")) {
                workers.removeIf(worker ->
                                filterValues.get(filterFields.indexOf("salary")).equals("null") ?
                                        worker.getSalary() != null :
                                        worker.getSalary() != Double.parseDouble(filterValues.get(filterFields.indexOf("salary"))));
            }
            if (filterFields.contains("startDate")) {
                workers.removeIf(w -> {
                    try {
                        return filterValues.get(filterFields.indexOf("startDate")).equals("null") ?
                                w.getStartDate() != null :
                                w.getStartDate() != new SimpleDateFormat("dd-MM-yyyy")
                                .parse(filterValues.get(filterFields.indexOf("startDate")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                });
            }
            if (filterFields.contains("endDate")) {
                workers.removeIf(w -> {
                    try {
                        return filterValues.get(filterFields.indexOf("endDate")).equals("null") ?
                                w.getEndDate() != null :
                                w.getEndDate() != new SimpleDateFormat("dd-MM-yyyy")
                                .parse(filterValues.get(filterFields.indexOf("endDate")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return true;
                });
            }
            if (filterFields.contains("position")) {
                workers.removeIf(worker -> filterValues.get(filterFields.indexOf("position")).equals("null") ?
                        worker.getPosition() != null :
                        worker.getPosition() != Position.getByTitle(filterValues.get(filterFields.indexOf("position"))));
            }
            if (filterFields.contains("status")) {
                workers.removeIf(worker -> filterValues.get(filterFields.indexOf("status")).equals("null") ?
                        worker.getStatus() != null :
                        worker.getStatus() != Status.getByTitle(filterValues.get(filterFields.indexOf("status"))));
            }
            if (filterFields.contains("annualTurnover")) {
                workers.removeIf(worker -> worker.getOrganization() == null
                        || worker.getOrganization().getAnnualTurnover() != Integer.parseInt(filterValues.get(filterFields.indexOf("annualTurnover"))));
            }
            if (filterFields.contains("employeesCount")) {
                workers.removeIf(worker -> worker.getOrganization() == null
                        || worker.getOrganization().getEmployeesCount() != Integer.parseInt(filterValues.get(filterFields.indexOf("employeesCount"))));
            }
            if (filterFields.contains("organizationType")) {
                workers.removeIf(worker -> worker.getOrganization() == null
                        || worker.getOrganization().getType() != OrganizationType.getByTitle(filterValues.get(filterFields.indexOf("organizationType"))));
            }
        }
        return workers;
    }
}
