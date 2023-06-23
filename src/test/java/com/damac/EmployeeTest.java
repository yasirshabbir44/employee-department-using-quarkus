package com.damac;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import model.Department;
import model.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class EmployeeTest {


    @Test
    @Transactional
    void testAssignDepartmentToEmployee() {
        // Create a test department
        Department department = new Department();
        department.setName( "IT");
        department.persist();

        // Create a test employee
        Employee employee = new Employee();
        employee.setName( "John");
        employee.setSalary(100.0);
        employee.setDateOfBirth(LocalDate.now());
        employee.persist();

        // Assign the department to the employee
        employee.setDepartment(department);
        employee.persist(employee);

        // Retrieve the employee from the database
        Employee savedEmployee = Employee.findById(employee.id);

        // Verify the department assignment
        assertNotNull(savedEmployee.getDepartment());
        assertEquals("IT", savedEmployee.getDepartment().getName());
        assertEquals(employee.id, savedEmployee.id);
    }
}
