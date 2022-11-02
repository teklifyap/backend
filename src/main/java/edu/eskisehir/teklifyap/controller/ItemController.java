package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<SuccessMessage> getItems(HttpServletRequest request) {

        User user = authorizationService.getUserFromHttpRequest(request);

        return ResponseEntity.ok(new SuccessMessage(itemService.getItems(user), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteItem(HttpServletRequest request, @PathVariable Long id) {

        User user = authorizationService.getUserFromHttpRequest(request);

        itemService.deleteItem(id, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateItem(HttpServletRequest request, @RequestBody ItemDto itemDto) {

        User user = authorizationService.getUserFromHttpRequest(request);

        itemService.updateItem(itemDto, user);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessMessage> getItem(HttpServletRequest request, @PathVariable Long id) {

        User user = authorizationService.getUserFromHttpRequest(request);

        return ResponseEntity.ok(new SuccessMessage(itemService.getItem(id, user), request.getServletPath()));
    }
}
