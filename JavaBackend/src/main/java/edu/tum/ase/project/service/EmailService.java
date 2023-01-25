package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class EmailService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    private JavaMailSender emailSender;

    public String sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        // TODO: read setFrom() value from application.properties
        message.setFrom("angryfish.project.ase@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

        return "all gucci";
    }

    public String sendDeliveryCreatedMail(String id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("customer with id %s not found", id));
        }

        String to = actor.get().getEmail();
        String subject = "Your delivery has been created";
        String text = "Your delivery has been created!";

        return sendSimpleMessage(to, subject, text);
    }

    public String sendDeliveryPlacedMail(String id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("customer with id %s not found", id));
        }

        String to = actor.get().getEmail();
        String subject = "Your delivery has arrived";
        String text = "Your delivery has arrived and has been placed into the pick-up box!";

        return sendSimpleMessage(to, subject, text);
    }

    public String sendDeliveryCollectedMail(String id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("customer with id %s not found", id));
        }

        String to = actor.get().getEmail();
        String subject = "You have collected your delivery";
        String text = "You have successfully collected your delivery from the pick-up box!";

        return sendSimpleMessage(to, subject, text);
    }
}