package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/item")
public class ItemController {

    private final ItemService itemService;
    private final AuthorizationService authorizationService;

    public ItemController(ItemService itemService, AuthorizationService authorizationService) {
        this.itemService = itemService;
        this.authorizationService = authorizationService;
    }

    @PostMapping
    public ResponseEntity<SuccessMessage> createItem(HttpServletRequest request, @RequestBody ItemDto itemDto) {

        User user = authorizationService.getUserFromHttpRequest(request);

        itemService.createItem(itemDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }
}
