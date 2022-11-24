package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.ItemDto;
import edu.eskisehir.teklifyap.domain.dto.ItemNameDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<SuccessDataMessage<List<ItemNameDto>>> getItems(HttpServletRequest request) {

        User user = authorizationService.getUserFromHttpRequest(request);

        return ResponseEntity.ok(new SuccessDataMessage<>(itemService.getItems(user), request.getServletPath()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessMessage> deleteItem(HttpServletRequest request, @PathVariable Long id) {

        authorizationService.getUserFromHttpRequest(request);

        itemService.deleteItem(id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessMessage> updateItem(HttpServletRequest request, @RequestBody ItemDto itemDto,
                                                     @PathVariable Long id) throws Exception {

        authorizationService.getUserFromHttpRequest(request);

        itemService.updateItem(itemDto, id);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDataMessage<ItemDto>> getItem(HttpServletRequest request, @PathVariable Long id)
            throws Exception {

        authorizationService.getUserFromHttpRequest(request);

        return ResponseEntity.ok(new SuccessDataMessage<>(itemService.getItem(id), request.getServletPath()));
    }

}
