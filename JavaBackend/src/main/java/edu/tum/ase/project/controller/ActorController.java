package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.service.ActorService;
import edu.tum.ase.project.utils.ActorType;
import edu.tum.ase.project.utils.ObjectDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/{actorType}")  // possible "Client", "Dispatcher", "Deliverer" or "AllActors"
public class ActorController {
    @Autowired
    ActorService actorService;

    private ActorType str2actorType(String actorTypeStr) {
        ActorType actorType;
        try {
            actorType = ActorType.valueOf(actorTypeStr);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Wrong actorType");
        }
        return actorType;
    }

    @GetMapping("/listAll")
    public List<Actor> getAllActors(@PathVariable(value = "actorType") final String actorTypeStr) {
        if (Objects.equals(actorTypeStr, "AllActors")){
            return actorService.getAllActors();
        }
        return actorService.getAllActorsByType(str2actorType(actorTypeStr));
    }

    @PostMapping("/create")
    public Actor createActor(@PathVariable(value = "actorType") final String actorTypeStr,
                             @RequestParam("email") String email, @RequestParam("pass") String pass) {
        return actorService.createActor(email, pass, str2actorType(actorTypeStr));
    }

    @PostMapping("/find")
    public Optional<Actor> getActorByEmail(@PathVariable(value = "actorType") final String actorTypeStr,
                                           @RequestParam("email") String email) {
        return actorService.findByActorTypeAndEmail(email, str2actorType(actorTypeStr));
    }

    @DeleteMapping("/delete")
    public void deleteActorById(@RequestParam("ActorId") String ActorId){
        try {
            actorService.deleteActor(ActorId);
            return;
        } catch (ObjectDoesNotExist e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
