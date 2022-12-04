package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.service.ActorService;
import edu.tum.ase.project.utils.ActorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/{actorType}")
public class ActorController {
    @Autowired
    ActorService actorService;

    @GetMapping("/listAll")
    public List<Actor> getAllActorsByType(@PathVariable(value = "actorType") final String actorTypeStr) {
        ActorType actorType = ActorType.valueOf(actorTypeStr);
        return actorService.getAllActorsByType(actorType);
    }

    @PostMapping("/create")
    public Actor createClient(@RequestBody String emil, @RequestBody String pass) {
        return actorService.createActor(emil, pass, ActorType.Client);
    }

    @GetMapping("/{clientEmail}")
    public Optional<Actor> getClientByEmail(@PathVariable(value = "clientEmail") final String email) {
        return actorService.findByEmail(email);
    }
}
