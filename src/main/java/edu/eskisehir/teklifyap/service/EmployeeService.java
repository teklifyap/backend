package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.dto.EmployeeNameDto;
import edu.eskisehir.teklifyap.domain.model.Employee;
import edu.eskisehir.teklifyap.domain.model.EmployeeHistory;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.EmployeeMapper;
import edu.eskisehir.teklifyap.repository.EmployeeHistoryRepository;
import edu.eskisehir.teklifyap.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
public class EmployeeService {

    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeHistoryRepository employeeHistoryRepository, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeHistoryRepository = employeeHistoryRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto, User user) {

        Employee employee = employeeMapper.toEmployee(employeeDto);

        employee.setUser(user);
        employee.setSalaryStartDate(LocalDate.now());
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
            employee.setSalaryStartDate(LocalDate.now());
        }
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    protected Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeDto getEmployee(Long id) throws Exception {
        return employeeMapper.toEmployeeDto(employeeRepository.findByIdAndDeletedFalse(id));
    }

    public double getEmployeeSalary(Long id) {
        double totalSalary = 0;
        double currentSalary = employeeRepository.findByIdAndDeletedFalse(id).getSalary();
        List<EmployeeHistory> salaryList = employeeHistoryRepository.findAllByEid(id);
        salaryList.sort(Comparator.comparing(EmployeeHistory::getEndDate).reversed());

        for (int i = 0; i < salaryList.size(); i++) {
            EmployeeHistory employeeHistory = salaryList.get(i);
            if (i == 0) {
                totalSalary += currentSalary * ChronoUnit.DAYS.between(employeeHistory.getStartDate(), LocalDate.now());
            } else {
                totalSalary += employeeHistory.getOldSalary() * ChronoUnit.DAYS.between(employeeHistory.getStartDate(), employeeHistory.getEndDate());
            }
        }
        return totalSalary;
    }
}
