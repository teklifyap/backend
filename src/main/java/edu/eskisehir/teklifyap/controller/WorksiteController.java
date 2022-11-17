package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.WorksiteDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.WorksiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<SuccessMessage> createWorksite(HttpServletRequest request, @RequestBody WorksiteDto worksiteDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.createWorksite(worksiteDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping
    public ResponseEntity<SuccessMessage> getWorksites(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessMessage(worksiteService.getWorksites(user), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteWorksite(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.deleteWorksite(id, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateWorksite(HttpServletRequest request, @RequestBody WorksiteDto worksiteDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        worksiteService.updateWorksite(worksiteDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessMessage> getWorksite(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessMessage(worksiteService.getWorksite(id, user), request.getServletPath()));
    }
}
