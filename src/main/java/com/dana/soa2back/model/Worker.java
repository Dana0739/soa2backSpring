package com.dana.soa2back.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table
@XmlRootElement
public class Worker {

    @Id
    @SequenceGenerator(name = "workersIdSeq", sequenceName = "workers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workersIdSeq")
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column
    @Positive
    private Double salary; //Поле может быть null, Значение поля должно быть больше 0

    @Column
    private Date startDate; //Поле может быть null

    @Column
    private Date endDate; //Поле может быть null

    @Column
    private Position position; //Поле может быть null

    @Column
    private Status status; //Поле может быть null

    @OneToOne(cascade = CascadeType.ALL)
    private Organization organization; //Поле может быть null

    public Worker() {
    }

    public Worker setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Worker setCoordinateX(Double x) {
        this.coordinates.setX(x);
        return this;
    }

    public Worker setCoordinateY(double y) {
        this.coordinates.setY(y);
        return this;
    }

    public Worker setOrganizationType(OrganizationType type) {
        this.organization.setType(type);
        return this;
    }

    public Worker setOrganizationType(String type) {
        this.organization.setType(type);
        return this;
    }

    public Worker setAnnualTurnover(Integer turnover) {
        this.organization.setAnnualTurnover(turnover);
        return this;
    }

    public Worker setEmployeesCount(int employeesCount) {
        this.organization.setEmployeesCount(employeesCount);
        return this;
    }

    public Worker setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public Worker setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Worker setId(long id) {
        this.id = id;
        return this;
    }

    public Worker setName(String name) {
        this.name = name;
        return this;
    }

    public Worker setOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public Worker setPosition(Position position) {
        this.position = position;
        return this;
    }

    public Worker setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Worker setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Worker))
            return false;
        Worker worker = (Worker) o;
        return Objects.equals(this.id, worker.id)
                && Objects.equals(this.name, worker.name)
                && Objects.equals(this.creationDate, worker.creationDate)
                && Objects.equals(this.startDate, worker.startDate)
                && Objects.equals(this.endDate, worker.endDate)
                && Objects.equals(this.organization, worker.organization)
                && Objects.equals(this.position, worker.position)
                && Objects.equals(this.salary, worker.salary)
                && Objects.equals(this.status, worker.status)
                && Objects.equals(this.coordinates, worker.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "<worker>" +
                "<id>" + this.id + "</id>" +
                "<name>" + this.name + "</name>" +
                "<coordinateX>" + this.coordinates.getX() + "</coordinateX>" +
                "<coordinateY>" + this.coordinates.getY() + "</coordinateY>" +
                "<creationDate>" + this.creationDate + "</creationDate>" +
                "<salary>" + ((this.salary == null) ? "" : this.salary) + "</salary>" +
                "<startDate>" + ((this.startDate == null) ? "" : this.startDate) + "</startDate>" +
                "<endDate>" + ((this.endDate == null) ? "" : this.endDate) + "</endDate>" +
                "<position>" + ((this.position == null) ? "" : this.position.getTitle()) + "</position>" +
                "<status>" + ((this.status == null) ? "" : this.status.getTitle()) + "</status>" +
                "<annualTurnover>" + ((this.organization == null) ? "" : this.organization.getAnnualTurnover()) + "</annualTurnover>" +
                "<employeesCount>" + ((this.organization == null) ? "" : this.organization.getEmployeesCount()) + "</employeesCount>" +
                "<organizationType>" + ((this.organization == null) ? "" : this.organization.getType().getTitle()) + "</organizationType>" +
                "</worker>";
    }
}
