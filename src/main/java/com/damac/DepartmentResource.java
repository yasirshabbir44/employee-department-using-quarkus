package com.damac;


import jakarta.transaction.Transactional;
import model.Department;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import jakarta.ws.rs.core.Response;

import java.util.List;

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
