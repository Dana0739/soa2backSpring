package com.dana.soa2back.controller;

import com.dana.soa2back.controller.exception.*;
import com.dana.soa2back.model.DTO.HRHireFireWorkerDTO;
import com.dana.soa2back.model.Worker;
import com.dana.soa2back.service.manager.WorkerManager;
import com.dana.soa2back.service.manager.WorkerValidator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("workers")
public class WorkerController {
    private final String RESPONSE_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>";
    private final String RESPONSE_END = "</response>";

    private final WorkerManager workerManager;

    WorkerController(WorkerManager workerManager) {
        this.workerManager = workerManager;
    }

    @GetMapping(value="/{id}",
            produces=MediaType.APPLICATION_XML_VALUE)
    String getById(@PathVariable Long id) {
        return RESPONSE_START
                + workerManager.findById(id).orElseThrow(() -> new WorkerNotFoundException(id)).toString()
                + RESPONSE_END;
    }

    @GetMapping(value="/max-salary",
            produces=MediaType.APPLICATION_XML_VALUE)
    String getMaxSalary() {
        return RESPONSE_START
                + workerManager.getMaxSalary().orElseThrow(WorkersNotFoundException::new).toString()
                + RESPONSE_END;
    }

    @GetMapping(value="/equal-salary",
            produces=MediaType.APPLICATION_XML_VALUE)
    String getWorkersCountWithEqualSalary(@RequestParam Double salary) {
        return RESPONSE_START + "<count>"
                + workerManager.getWorkersCountWithEqualSalary(salary)
                + "</count>" + RESPONSE_END;
    }

    @GetMapping(value="/name-starts-with",
            produces=MediaType.APPLICATION_XML_VALUE)
    String getWorkersWithNameStartsWithPrefix(@RequestParam String prefix) {
        List<Worker> workers = workerManager.getWorkersWithNameStartsWithPrefix(prefix);
        if (!workers.isEmpty()) {
            return RESPONSE_START
                    + workers.stream().map(Worker::toString).collect(Collectors.joining())
                    + RESPONSE_END;
        } else {
            throw new WorkersNotFoundException();
        }
    }

    @GetMapping(produces=MediaType.APPLICATION_XML_VALUE)
    String getWorkersFilterSortPaging(@RequestParam(name= "filterFields", required=false) List<String> filterFields,
                   @RequestParam(name="filterValues", required=false) List<String> filterValues,
                   @RequestParam(name="sortFields", required=false) List<String> sortFields,
                   @RequestParam(name="pageNumber", required=false) Integer pageNumber,
                   @RequestParam(name="pageSize", required=false) Integer pageSize) {
        if (!WorkerValidator.checkParametersForFilterSort(pageSize, pageNumber, filterFields, filterValues, sortFields))
            throw new IllegalWorkerFilterSortPagingArgumentsException();
        List<Worker> workers = workerManager.getAllWorkers(filterFields, filterValues, sortFields);
        workers = workerManager.pageWorkersList(workers, pageNumber, pageSize);
        if (workers.isEmpty()) throw new WorkersNotFoundException();
        return RESPONSE_START
                + workers.stream().map(Worker::toString).collect(Collectors.joining())
                + RESPONSE_END;
    }

    @PostMapping(produces=MediaType.APPLICATION_XML_VALUE,
            consumes=MediaType.APPLICATION_XML_VALUE)
    String addWorker(@Valid @RequestBody Worker newWorker) {
        if (WorkerValidator.checkWorkerContainsForbiddenFields(newWorker, true))
            throw new IllegalWorkerArgumentsException();
        if (!WorkerValidator.validateWorker(newWorker)) throw new WorkerNotValidException();
        return RESPONSE_START
                + workerManager.saveWorker(newWorker).toString()
                + RESPONSE_END;
    }

    @PostMapping(value="/{id}",
            produces=MediaType.APPLICATION_XML_VALUE,
            consumes=MediaType.APPLICATION_XML_VALUE)
    String editWorker(@RequestBody HRHireFireWorkerDTO hrHireFireWorkerDTO, @PathVariable long id) {
        if (!workerManager.workerExistsById(id)) throw new WorkerNotFoundException(id);
        if (hrHireFireWorkerDTO.getOrganizationId() != null
                && !workerManager.organizationExistsById(hrHireFireWorkerDTO.getOrganizationId()))
            throw new OrganizationNotFoundException(hrHireFireWorkerDTO.getOrganizationId());
        return RESPONSE_START
                + workerManager.editWorker(hrHireFireWorkerDTO, id).toString()
                + RESPONSE_END;
    }

    @PutMapping(value="/{id}",
            produces=MediaType.APPLICATION_XML_VALUE,
            consumes=MediaType.APPLICATION_XML_VALUE)
    String replaceWorker(@Valid @RequestBody Worker newWorker, @PathVariable Long id) {
        if (!workerManager.workerExistsById(id)) throw new WorkerNotFoundException(id);
        if (WorkerValidator.checkWorkerContainsForbiddenFields(newWorker)) throw new IllegalWorkerArgumentsException();
        if (!WorkerValidator.validateWorker(newWorker)) throw new WorkerNotValidException();
        newWorker.setId(id);
        return RESPONSE_START
                + workerManager.saveWorker(newWorker).toString()
                + RESPONSE_END;
    }

    @DeleteMapping("/{id}")
    void deleteWorker(@PathVariable Long id) {
        if (workerManager.workerExistsById(id)) {
            workerManager.deleteWorkerById(id);
        } else {
            throw new WorkerNotFoundException(id);
        }
    }
}
