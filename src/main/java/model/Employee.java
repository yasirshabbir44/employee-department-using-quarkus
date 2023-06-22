package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@ToString
public class Employee extends PanacheEntity {
    // Define employee properties, getters, and setters

    private String name;
    private Double salary;
    private LocalDate dateOfBirth;


    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;
}