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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    @GET
    public Response getAllDepartments() {
        // Implement logic to fetch all departments
        List<DepartmentDTO> departmentDTOS = Department.listAll().stream().map(depar -> {
            Department department = (Department) depar;
            DepartmentDTO departmentDTO = MapperService.convertToDepartmentDTO(department);
            departmentDTO.setEmployees(MapperService.convertToEmployeeDTOList(department.getEmployees()));
            return departmentDTO;
        }).toList();
        return Response.ok(departmentDTOS).build();
    }

    @GET
    @Path("/{id}")
    public Response getDepartmentById(@PathParam("id") Long id) {
        // Implement logic to fetch a department by ID
        Department department = Department.findById(id);
        DepartmentDTO departmentDTO = MapperService.convertToDepartmentDTO(department);
        departmentDTO.setEmployees(MapperService.convertToEmployeeDTOList(department.getEmployees()));
        return Response.ok(departmentDTO).build();
    }


    @GET
    @Path("/max-salary-employees")
    public Response getDepartmentsWithMaxSalaryEmployees() {

        List<DepartmentDTO> departmentDTOs = Department.listAll().parallelStream()
                .map(panacheEntityBase -> {
                    Department department = (Department) panacheEntityBase;
                    final double maxSalary = department.getEmployees()
                            .stream()
                            .mapToDouble(Employee::getSalary)
                            .max()
                            .orElse(0.0);

                    AtomicReference<Double> totalSalary = new AtomicReference<>(0.0);

                    List<EmployeeDTO> employeesWithMaxSalary = department.getEmployees()
                            .parallelStream()
                            .filter(employee -> {
                                totalSalary.set(totalSalary.get() + employee.getSalary());
                                return employee.getSalary() == maxSalary;
                            })
                            .map(MapperService::convertToEmployeeDTO)
                            .collect(Collectors.toList());


                    return DepartmentDTO.builder()
                            .id(department.id)
                            .name(department.getName())
                            .employees(employeesWithMaxSalary)
                            .noOfEmployee(department.getEmployees().size())
                            .totalSalary(totalSalary.get())
                            .build();
                }).toList();

        return Response.ok(departmentDTOs).build();
    }

    @POST
    @Transactional
    public Response createDepartment(DepartmentDTO departmentDTO) {
        // Implement logic to create a new department
        Department department = MapperService.convertToDepartment(departmentDTO);
        department.persist();
        departmentDTO = MapperService.convertToDepartmentDTO(department);
        return Response.ok(departmentDTO).build();
    }

    @PUT
    @Transactional
    public Response updateDepartment(DepartmentDTO departmentDTO) {
        // Implement logic to update an existing department
        Department department = (Department) Department.findByIdOptional(departmentDTO.getId())
                .orElseThrow(() -> {
                    throw new NotFoundException("No departments found");
                });
        if (department != null) {
            department.setName(department.getName());
            department.persist();
        }

        return Response.ok(MapperService.convertToDepartmentDTO(department)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDepartment(@PathParam("id") Long id) {
        // Implement logic to delete a department by ID
        Department department = Department.findById(id);
        if (department != null) {
            department.delete();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
