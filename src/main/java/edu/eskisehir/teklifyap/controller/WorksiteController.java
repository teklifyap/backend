package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.CreateWorksiteDto;
import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.WorksiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v2/worksite")
public class WorksiteController {

    private final WorksiteService worksiteService;
    private final AuthorizationService authorizationService;

    public WorksiteController(WorksiteService worksiteService, AuthorizationService authorizationService) {
        this.worksiteService = worksiteService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<SuccessMessage> createWorksite(HttpServletRequest request, @RequestBody CreateWorksiteDto createWorksiteDto) throws Exception {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.createWorksite(createWorksiteDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping
    public ResponseEntity<SuccessDataMessage<List<WorksiteDto>>> getWorksites(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(worksiteService.getWorksites(user), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteWorksite(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.deleteWorksite(id, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateWorksite(HttpServletRequest request, @RequestBody WorksiteDto worksiteDto, @PathVariable Long id) {
        authorizationService.getUserFromHttpRequest(request);
        worksiteService.updateWorksite(id, worksiteDto);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDataMessage<WorksiteDto>> getWorksite(HttpServletRequest request, @PathVariable Long id) {
        authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(worksiteService.getWorksite(id), request.getServletPath()));
    }

    @PostMapping("/{id}/employee")
    public ResponseEntity<SuccessMessage> addEmployee(HttpServletRequest request, @PathVariable Long id, @RequestBody Long employeeId) {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.addEmployee(id, employeeId, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @DeleteMapping("/{id}/employee/{employeeId}")
    public ResponseEntity<SuccessMessage> deleteEmployee(HttpServletRequest request, @PathVariable Long id, @PathVariable Long employeeId) {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.removeEmployee(id, employeeId, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }
}
