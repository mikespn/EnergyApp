package org.energyapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energyapp.model.TestUser;
import org.energyapp.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestApiController {

    private final TestService testService;

    @PostMapping("/api/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody TestUser user) {
        testService.registerUser(user);
        Map<String, String> response =
                Map.of("message", "User: " + user.getUsername() + " successfully registered");
        return ResponseEntity.ok().body(response);
    }


}
