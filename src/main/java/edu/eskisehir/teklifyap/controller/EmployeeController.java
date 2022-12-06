package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.dto.EmployeeNameDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v2/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final AuthorizationService authorizationService;

    public EmployeeController(EmployeeService employeeService, AuthorizationService authorizationService) {
        this.employeeService = employeeService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDataMessage<EmployeeDto>> getEmployee(HttpServletRequest request, @PathVariable Long id) throws Exception {
        authorizationService.getUserFromHttpRequest(request);

        return ResponseEntity.ok(new SuccessDataMessage<>(employeeService.getEmployee(id), request.getServletPath()));
    }

    @PostMapping
    public ResponseEntity<SuccessMessage> createEmployee(HttpServletRequest request, @RequestBody EmployeeDto employeeDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        employeeService.createEmployee(employeeDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteEmployee(HttpServletRequest request, @PathVariable Long id) throws Exception {
        authorizationService.getUserFromHttpRequest(request);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping
    public ResponseEntity<SuccessDataMessage<List<EmployeeNameDto>>> getEmployees(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(employeeService.getEmployees(user), request.getServletPath()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateEmployee(HttpServletRequest request, @PathVariable Long id, @RequestBody EmployeeDto employeeDto) throws Exception {
        authorizationService.getUserFromHttpRequest(request);
        employeeService.updateEmployee(employeeDto, id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }


}
