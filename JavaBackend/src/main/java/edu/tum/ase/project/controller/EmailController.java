package com.SpringBootEmail.controller;

import com.SpringBootEmail.Entity.EmailDetails;
import com.SpringBootEmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class EmailController {

    @Autowired private EmailService emailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody ObjectNode json) {
        /*
        curl -X POST -H "Content-Type: application/json" \
        -d '{"to": <TO>, "subject":<SUBJECT>, "clientId": <TEXT>}' \
        localhost:8080/sendMail
        */

        String to;
        String subject;
        String text;

        try {
            to = json.get("to").asText();
            subject = json.get("subject").asText();
            text = json.get("text").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }

        String status = emailService.sendSimpleMessage(to, subject, from);

        return status;
    }
}