package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.*;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v2/offer")
public class OfferController {

    private final OfferService offerService;
    private final AuthorizationService authorizationService;

    public OfferController(OfferService offerService, AuthorizationService authorizationService) {
        this.offerService = offerService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDataMessage<OfferDto>> getOffer(HttpServletRequest request, @PathVariable Long id)
            throws Exception {
        authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(offerService.getOffer(id), request.getServletPath()));
    }

    @PostMapping
    public ResponseEntity<SuccessMessage> makeOffer(HttpServletRequest request, @RequestBody MakeOfferDto makeOffer)
            throws Exception {

        User user = authorizationService.getUserFromHttpRequest(request);
        offerService.makeOffer(makeOffer, user);

        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteOffer(HttpServletRequest request, @PathVariable Long id) {
        authorizationService.getUserFromHttpRequest(request);
        offerService.deleteOffer(id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateOffer(HttpServletRequest request, @RequestBody UpdateOfferDto body,
                                                      @PathVariable Long id) throws Exception {
        authorizationService.getUserFromHttpRequest(request);
        offerService.updateOffer(id, body);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping
    public ResponseEntity<SuccessDataMessage<List<OfferDto>>> getOffers(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(offerService.getOffers(user), request.getServletPath()));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<SuccessMessage> updateOfferStatus(HttpServletRequest request, @PathVariable Long id)
            throws Exception {
        authorizationService.getUserFromHttpRequest(request);
        offerService.updateOfferStatus(id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PostMapping("/item")
    public ResponseEntity<SuccessMessage> addItemsToOffer(HttpServletRequest request, @RequestBody UpdateOfferItemDto body,
                                                          @RequestParam("offer") Long offerId) throws Exception {

        authorizationService.getUserFromHttpRequest(request);

        offerService.addItemsToOffer(body, offerId);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @DeleteMapping("/item/{iid}")
    public ResponseEntity<SuccessMessage> deleteItemFromOffer(HttpServletRequest request, @PathVariable Long iid,
                                                              @RequestParam("offer") Long oid) {
        authorizationService.getUserFromHttpRequest(request);
        offerService.deleteItemFromOffer(iid, oid);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

}
