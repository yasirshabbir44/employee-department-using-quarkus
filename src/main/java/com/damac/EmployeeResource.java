package com.damac;

import com.damac.service.MapperService;
import dto.DepartmentDTO;
import dto.EmployeeDTO;
import io.quarkus.panache.common.Parameters;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import jakarta.ws.rs.core.Response;
import model.Department;
import model.Employee;

import java.util.List;
import java.util.Optional;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @GET
    public List<EmployeeDTO> getAllEmployees() {
        // Implement logic to fetch all employees from the database or any other data source

        return MapperService.convertToEmployeeDTOList(Employee.listAll());
    }

    @GET
    @Path("/{id}")
    public EmployeeDTO getEmployeeById(@PathParam("id") Long id) {
        // Implement logic to fetch an employee by ID
        return MapperService.convertToEmployeeDTO(Employee.findById(id));
    }

    @POST
    @Transactional
    public Response createEmployee(EmployeeDTO employeeDTO) {
        // Implement logic to create a new employee
        // Return a response with HTTP status code and, if needed, the newly created employee

        Employee employee = MapperService.convertToEmployee(employeeDTO);

        System.out.println(employee.toString());
        // Check if the department exists
        String departmentName = Optional.ofNullable(employeeDTO.getDepartment()).map(DepartmentDTO::getName).orElse("");
        System.out.println(departmentName);


        Department department = Department.find("name = :name", Parameters.with("name", departmentName)).firstResult();

        if (department == null) {
            // Department doesn't exist, create a new one
            department = new Department();
            department.setName(employeeDTO.getDepartment().getName());
            department.persist();
        }

        employee.setDepartment(department);
        employee.persistAndFlush();

        return Response.status(Response.Status.CREATED).entity(MapperService.convertToEmployeeDTO(employee)).build();

    }

    @PUT
    @Path("/{id}")
    public Employee updateEmployee(@PathParam("id") Long id, EmployeeDTO updatedEmployee) {
        // Implement logic to update an existing employee
        // Return a response with HTTP status code and, if needed, the updated employee
        Employee employee = Employee.findById(id);
        if (employee != null) {
            employee.setName(updatedEmployee.getName());
            employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
            employee.setSalary(updatedEmployee.getSalary());
            employee.persist();
        }
        return employee;
    }

    @DELETE
    @Path("/{id}")
    public void deleteEmployee(@PathParam("id") Long id) {
        // Implement logic to delete an employee by ID
        // Return a response with HTTP status code
        Employee employee = Employee.findById(id);
        if (employee != null) {
            employee.delete();
        }
    }


}
