package com.dana.soa2back.controller;

import com.dana.soa2back.controller.exception.OrganizationNotFoundException;
import com.dana.soa2back.controller.exception.OrganizationNotValidException;
import com.dana.soa2back.controller.exception.OrganizationsNotFoundException;
import com.dana.soa2back.model.Organization;
import com.dana.soa2back.service.repository.OrganizationRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("organizations")
public class OrganizationController {
    private final String RESPONSE_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response>";
    private final String RESPONSE_END = "</response>";

    private final OrganizationRepository organizationRepository;

    public OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @GetMapping(produces=MediaType.APPLICATION_XML_VALUE)
    String getAll() {
        List<Organization> organizations = organizationRepository.findAll();
        if (organizations.isEmpty()) throw new OrganizationsNotFoundException();
        return RESPONSE_START
                + organizations.stream().map(Organization::toString).collect(Collectors.joining())
                + RESPONSE_END;
    }

    @GetMapping(value="/{id}",
            produces= MediaType.APPLICATION_XML_VALUE)
    String getById(@PathVariable Long id) {
        return RESPONSE_START
                + organizationRepository.findById(id)
                    .orElseThrow(() -> new OrganizationNotFoundException(id)).toString()
                + RESPONSE_END;
    }

    @PostMapping(produces=MediaType.APPLICATION_XML_VALUE,
            consumes=MediaType.APPLICATION_XML_VALUE)
    String addOrganization(@Valid @RequestBody Organization newOrganization) {
        if (!ValidateOrganization(newOrganization, false)) throw new OrganizationNotValidException();
        return RESPONSE_START
                + organizationRepository.save(newOrganization).toString()
                + RESPONSE_END;
    }

    @PutMapping(value="/{id}",
            produces=MediaType.APPLICATION_XML_VALUE,
            consumes=MediaType.APPLICATION_XML_VALUE)
    String replaceOrganization(@Valid @RequestBody Organization newOrganization, @PathVariable Long id) {
        if (!organizationRepository.existsById(id)) throw new OrganizationNotFoundException(id);
        newOrganization.setId(id);
        if (!ValidateOrganization(newOrganization, true)) throw new OrganizationNotValidException();
        return RESPONSE_START
                + organizationRepository.save(newOrganization).toString()
                + RESPONSE_END;
    }

    @DeleteMapping("/{id}")
    void deleteOrganization(@PathVariable Long id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
        } else {
            throw new OrganizationNotFoundException(id);
        }
    }

    private boolean ValidateOrganization(Organization organization, boolean idNeeded) {
        boolean result;
        System.out.println(organization.toString());
        try {
            result = organization.getEmployeesCount() > 0 &&
                    organization.getAnnualTurnover() > 0 &&
                    (!idNeeded || organization.getId() > 0) &&
                    organization.getType() != null;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
