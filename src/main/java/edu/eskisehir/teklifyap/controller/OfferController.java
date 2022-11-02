package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.OfferDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/offer")
public class OfferController {
    private final OfferService offerService;
    private final AuthorizationService authorizationService;

    public OfferController(OfferService offerService, AuthorizationService authorizationService) {
        this.offerService = offerService;
        this.authorizationService = authorizationService;
    }


    @PostMapping
    public ResponseEntity<SuccessMessage> createOffer(HttpServletRequest request, @RequestBody OfferDto offerDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        offerService.createOffer(offerDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping
    public ResponseEntity<SuccessMessage> getOffers(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessMessage(offerService.getOffers(user), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteOffer(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        offerService.deleteOffer(id, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateOffer(HttpServletRequest request, @RequestBody OfferDto offerDto) {
        User user = authorizationService.getUserFromHttpRequest(request);
        offerService.updateOffer(offerDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessMessage> getOffer(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessMessage(offerService.getOffer(id, user), request.getServletPath()));
    }
}
