package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Department extends PanacheEntity {

    private String name;

    // Define department properties, getters, and setters
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Employee> employees;
}