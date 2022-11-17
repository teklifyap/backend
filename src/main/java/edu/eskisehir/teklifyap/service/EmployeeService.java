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
        return null;
    }

    public String getEmployees() {
        return null;
    }

    public void deleteEmployee(Long id, User user) {
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(EmployeeDto employeeDto, User user) {

    }

    public String getEmployee(Long id, User user) {
        return null;
    }
}
