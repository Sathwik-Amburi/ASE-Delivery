package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.repository.ActorRepository;
import edu.tum.ase.project.utils.ActorType;
import edu.tum.ase.project.utils.PropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class AuthService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    private PropertiesReader props;

    public String authenticateActor(String email, String password) {
        Optional<Actor> actor = actorRepository.findByEmailAndPass(email, password);
        if (actor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("customer %s not found", email));
        }

        return "Authenticated";
        //return new ResponseEntity<String>("Authenticated", HttpStatus.CREATED);
    }
}