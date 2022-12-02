package edu.eskisehir.teklifyap.mapper;

import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.dto.EmployeeNameDto;
import edu.eskisehir.teklifyap.domain.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeDto employeeDto);

    EmployeeDto toEmployeeDto(Employee employee);

    List<EmployeeDto> toEmployeeDto(List<Employee> employee);

    List<Employee> toEmployee(List<EmployeeDto> employeeDto);

    List<EmployeeNameDto> toEmployeeNameDto(List<Employee> employee);

}
