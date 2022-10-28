package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.WelcomeMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@AllArgsConstructor
public class HomeController {

    @GetMapping
    public ResponseEntity<WelcomeMessage> home() {
        return ResponseEntity.ok(new WelcomeMessage());
    }

}
