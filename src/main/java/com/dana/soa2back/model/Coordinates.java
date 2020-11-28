package com.dana.soa2back.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Objects;

@Data
@Entity
@Table
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private Double x; //Поле не может быть null

    @Column(nullable = false)
    @Max(value = 444, message = "maximum value is 444")
    private double y; //Максимальное значение поля: 444

    public Coordinates(Double x) {
        this(x, 0);
    }

    public Coordinates(Double x, double y) {
        this.x = x;
        this.y = y;
        if (this.x == null) throw new IllegalArgumentException("Parameter x must not be null");
    }

    public Coordinates() {

    }

    public Coordinates setX(Double x) {
        if (x == null) throw new IllegalArgumentException("Parameter x must not be null");
        this.x = x;
        return this;
    }

    public Coordinates setY(double y) {
        this.y = y;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Coordinates))
            return false;
        Coordinates coordinates = (Coordinates) o;
        return Objects.equals(this.x, coordinates.x)
                && Objects.equals(this.y, coordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
