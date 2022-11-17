package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.MakeOfferDto;
import edu.eskisehir.teklifyap.domain.dto.OfferDto;
import edu.eskisehir.teklifyap.domain.dto.OfferItemDto;
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

    @GetMapping
    public ResponseEntity<SuccessDataMessage<List<OfferDto>>> getOffers(HttpServletRequest request) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(offerService.getOffers(user), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteOffer(HttpServletRequest request, @PathVariable Long id) {
        authorizationService.getUserFromHttpRequest(request);
        offerService.deleteOffer(id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateOffer(HttpServletRequest request, @RequestBody OfferDto offerDto) {
        authorizationService.getUserFromHttpRequest(request);
        offerService.updateOffer(offerDto);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDataMessage<OfferDto>> getOffer(HttpServletRequest request, @PathVariable Long id) {
        User user = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(offerService.getOffer(user), request.getServletPath()));
    }

    @PostMapping
    public ResponseEntity<SuccessMessage> makeOffer(HttpServletRequest request, @RequestBody MakeOfferDto makeOffer) {

        User user = authorizationService.getUserFromHttpRequest(request);
        offerService.makeOffer(makeOffer, user);

        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PostMapping("/items")
    public ResponseEntity<SuccessMessage> addItemsToOffer(HttpServletRequest request, @RequestBody OfferItemDto offerItemDto) {

        authorizationService.getUserFromHttpRequest(request);
//        offerService.updateOffer(OfferDto.builder()
//                .id(offerItemDto.getOid())
//                .items(offerItemDto.getItems())
//                .build());

        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

}
