package com.dana.soa2back.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Data
@Entity
@Table
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    @Positive
    private Integer annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0

    @Column(nullable = false)
    @Positive
    private int employeesCount; //Значение поля должно быть больше 0

    @Column(nullable = false)
    private OrganizationType type; //Поле не может быть null

    public Organization(Integer annualTurnover, String type) {
        this(annualTurnover, 1, type);
    }

    public Organization(Integer annualTurnover, int employeesCount, String type) {
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = OrganizationType.getByTitle(type);
        if (employeesCount <= 0) throw new IllegalArgumentException("Parameter employeesCount must be more 0");
        if (annualTurnover == null) throw new IllegalArgumentException("Parameter annualTurnover must not be null");
        if (this.type == null) throw new IllegalArgumentException("Parameter organizationType must be in: "
                + String.join(", ", OrganizationType.getAll()));
    }

    public Organization() {

    }

    public Organization setAnnualTurnover(Integer annualTurnover) {
        this.annualTurnover = annualTurnover;
        return this;
    }

    public Organization setEmployeesCount(int employeesCount) {
        if (employeesCount <= 0) throw new IllegalArgumentException("Parameter employeesCount must be more 0");
        this.employeesCount = employeesCount;
        return this;
    }

    public Organization setType(String type) {
        return setType(OrganizationType.getByTitle(type));
    }

    public Organization setType(OrganizationType type) {
        if (type == null) throw new IllegalArgumentException("Parameter organizationType must be in: "
                + String.join(", ", OrganizationType.getAll()));
        this.type = type;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Organization))
            return false;
        Organization organization = (Organization) o;
        return Objects.equals(this.annualTurnover, organization.annualTurnover)
                && Objects.equals(this.employeesCount, organization.employeesCount)
                && Objects.equals(this.type, organization.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.annualTurnover, this.employeesCount, this.type);
    }
}
