package com.damac.service;

import dto.DepartmentDTO;
import dto.EmployeeDTO;
import model.Department;
import model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class MapperService {



    public static EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.id);
        dto.setSalary(employee.getSalary());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setName(employee.getName());
        dto.setDepartment(convertToDepartmentDTO(employee.getDepartment()));
        return dto;
    }


    public static Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.id = employeeDTO.getId();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        return employee;
    }

    public static DepartmentDTO convertToDepartmentDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.id);
        dto.setName(department.getName());
        return dto;
    }

    public static Department convertToDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.id = departmentDTO.getId();
        department.setName(departmentDTO.getName());
        return department;
    }


    public static List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> employees) {
        return employees.stream()
                .map(MapperService::convertToEmployeeDTO)
                .collect(Collectors.toList());
    }
}
