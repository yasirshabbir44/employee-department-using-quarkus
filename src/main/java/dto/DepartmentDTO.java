package dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import model.Employee;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO{

    private Long id;
    private String name;
    private List<EmployeeDTO> employees;
    private Integer noOfEmployee;
    private Double totalSalary;
}