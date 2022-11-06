package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.model.Employee;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.repository.EmployeeRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto, User user) {
        Employee employee = Employee.builder()
                .name(employeeDto.getName())
                .surname(employeeDto.getSurname())
                .user(user)
                .build();
        return EmployeeDto.builder()
                .id(employeeRepository.save(employee).getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .build();
    }
    public String getEmployees() {
        return employeeRepository.findAll().stream().map(employee -> EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .build()).toList().toString();
    }

    public void deleteEmployee(Long id, User user) {
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(EmployeeDto employeeDto, User user) {
    employeeRepository.findById(employeeDto.getId()).ifPresent(employee -> {
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employeeRepository.save(employee);
    });
    }

    public String getEmployee(Long id, User user) {
        return employeeRepository.findById(id).map(employee -> EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .build()).toString();
    }
}
