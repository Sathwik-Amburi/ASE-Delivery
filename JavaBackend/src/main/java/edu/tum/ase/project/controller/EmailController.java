package edu.tum.ase.project.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.tum.ase.project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class EmailController {

    @Autowired private EmailService emailService;

    /*
    curl -X POST -H "Content-Type: application/json" \
    -d '{"id": <ID>}' \
    localhost:8080/email/delivery-created
    */

    @PostMapping("/email/delivery-created")
    public String sendDeliveryCreatedById(@RequestBody ObjectNode json) {
        String id;

        try {
            id = json.get("id").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }

        return emailService.sendDeliveryCreatedMail(id);
    }

    @PostMapping("/email/delivery-placed")
    public String sendDeliveryPlacedById(@RequestBody ObjectNode json) {
        String id;

        try {
            id = json.get("id").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }

        return emailService.sendDeliveryPlacedMail(id);
    }

    @PostMapping("/email/delivery-collected")
    public String sendDeliveryCollectedById(@RequestBody ObjectNode json) {
        String id;

        try {
            id = json.get("id").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }

        return emailService.sendDeliveryCollectedMail(id);
    }
}