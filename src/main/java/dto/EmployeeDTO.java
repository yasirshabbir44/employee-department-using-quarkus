package dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String name;
    private Double salary;
    private LocalDate dateOfBirth;
    private DepartmentDTO department;

}
