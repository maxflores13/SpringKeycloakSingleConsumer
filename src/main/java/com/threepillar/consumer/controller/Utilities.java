package com.threepillar.consumer.controller;

import com.threepillar.consumer.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class Utilities {

    @Autowired
    KeycloakService keycloakService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumeSingleUser")
    public ResponseEntity<String> consumeSingleUser() {
        String token = keycloakService.getSessionToken().getAccessToken();

        var headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "bearer " + token);
        var entity = new HttpEntity<>(headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate
                    .exchange("http://localhost:8081/producer/api/operationSingleUser", HttpMethod.GET, entity, String.class);

            return ResponseEntity.ok("Message retrieved: " + response.getBody());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
