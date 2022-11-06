package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.EmployeeDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final AuthorizationService authorizationService;

    public EmployeeController(EmployeeService employeeService, AuthorizationService authorizationService) {
        this.employeeService = employeeService;
        this.authorizationService = authorizationService;
    }


    @PostMapping
    public ResponseEntity<SuccessMessage> createEmployee(HttpServletRequest request, @RequestBody EmployeeDto employeeDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        employeeService.createEmployee(employeeDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping
    public ResponseEntity<SuccessMessage> getEmployees(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessMessage(employeeService.getEmployees(), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteEmployee(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        employeeService.deleteEmployee(id, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateEmployee(HttpServletRequest request, @RequestBody EmployeeDto employeeDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        employeeService.updateEmployee(employeeDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessMessage> getEmployee(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessMessage(employeeService.getEmployee(id, user), request.getServletPath()));
    }

}
