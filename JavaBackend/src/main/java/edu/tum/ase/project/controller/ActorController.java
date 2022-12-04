package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.service.ActorService;
import edu.tum.ase.project.utils.ActorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/{actorType}")
public class ActorController {
    @Autowired
    ActorService actorService;

    private ActorType str2actorType(String actorTypeStr) {
        ActorType actorType;
        try {
            actorType = ActorType.valueOf(actorTypeStr);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return actorType;
    }

    @GetMapping("/listAll")
    public List<Actor> getAllActorsByType(@PathVariable(value = "actorType") final String actorTypeStr) {
        return actorService.getAllActorsByType(str2actorType(actorTypeStr));
    }

    @PostMapping("/create")
    public Actor createActor(@PathVariable(value = "actorType") final String actorTypeStr,
                             @RequestBody String email, @RequestBody String pass) {
        return actorService.createActor(email, pass, str2actorType(actorTypeStr));
    }

    @PostMapping("/find")
    public Optional<Actor> getActorByEmail(@PathVariable(value = "actorType") final String actorTypeStr,
                                           @RequestBody String email) {
        return actorService.findByActorTypeAndEmail(email, str2actorType(actorTypeStr));
    }
}
