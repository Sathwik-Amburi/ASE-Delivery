package edu.tum.ase.project.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.tum.ase.project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/auth")
    public String authenticate(@RequestBody ObjectNode json) {
        String email;
        String password;

        try {
            email = json.get("email").asText();
            password = json.get("password").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }

        return authService.authenticateActor(email, password);
    }
}