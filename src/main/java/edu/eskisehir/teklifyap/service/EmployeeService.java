package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.dto.EmployeeNameDto;
import edu.eskisehir.teklifyap.domain.model.Employee;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.EmployeeMapper;
import edu.eskisehir.teklifyap.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto, User user) {
        Employee employee = employeeMapper.toEmployee(employeeDto);
        employee.setUser(user);
        employee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    public List<EmployeeNameDto> getEmployees(User user) {
        List<Employee> all = employeeRepository.findAllByUserIdAndDeletedFalse(user.getId());
        return employeeMapper.toEmployeeNameDto(all);
    }

    public void deleteEmployee(Long id) throws Exception {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new Exception("EmployeeNotFound!"));
        employee.setDeleted(true);
        save(employee);
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long id) throws Exception {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new Exception("EmployeeNotFound!"));
        System.out.println(employeeDto.toString());
        if (employeeDto.getName() != null) {
            employee.setName(employeeDto.getName());
        }
        if (employeeDto.getSurname() != null) {
            employee.setSurname(employeeDto.getSurname());
        }
        if (employeeDto.getSalary() != employee.getSalary() && employeeDto.getSalary() != 0) {
            employee.setSalary(employeeDto.getSalary());
        }
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    protected Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeDto getEmployee(Long id) throws Exception {
        return employeeRepository.findById(id).map(employeeMapper::toEmployeeDto).orElseThrow(() -> new Exception("EmployeeNotFound!"));
    }
}
