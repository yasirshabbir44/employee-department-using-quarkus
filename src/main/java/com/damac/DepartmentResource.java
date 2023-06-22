package com.damac;


import com.damac.service.MapperService;
import dto.DepartmentDTO;
import dto.EmployeeDTO;
import jakarta.transaction.Transactional;
import model.Department;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import jakarta.ws.rs.core.Response;
import model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {
    @GET
    public List<Department> getAllDepartments() {
        // Implement logic to fetch all departments
        return Department.listAll();
    }

    @GET
    @Path("/{id}")
    public Department getDepartmentById(@PathParam("id") Long id) {
        // Implement logic to fetch a department by ID
        return Department.findById(id);
    }


    @GET
    @Path("/max-salary-employees")
    public Response getDepartmentsWithMaxSalaryEmployees() {
        List<Department> departments = Department.listAll();

        List<DepartmentDTO> departmentDTOs = new ArrayList<>();

        for (Department department : departments) {
            double maxSalary = department.getEmployees()
                    .stream()
                    .mapToDouble(Employee::getSalary)
                    .max()
                    .orElse(0.0);

            List<EmployeeDTO> employeesWithMaxSalary = department.getEmployees()
                    .stream()
                    .filter(employee -> employee.getSalary() == maxSalary)
                    .map(MapperService::convertToEmployeeDTO)
                    .collect(Collectors.toList());

            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.id);
            departmentDTO.setName(department.getName());
            departmentDTO.setEmployees(employeesWithMaxSalary);

            departmentDTOs.add(departmentDTO);
        }

        return Response.ok(departmentDTOs).build();
    }

    @POST
    @Transactional
    public Department createDepartment(Department department) {
        // Implement logic to create a new department
        department.persist();
        return department;
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public void updateDepartment(@PathParam("id") Long id, Department updatedDepartment) {
        // Implement logic to update an existing department
        Department department = Department.findById(id);
        if (department != null) {
            department.setName(updatedDepartment.getName());
            department.persist();
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteDepartment(@PathParam("id") Long id) {
        // Implement logic to delete a department by ID
        Department department = Department.findById(id);
        if (department != null) {
            department.delete();
        }
    }
}
