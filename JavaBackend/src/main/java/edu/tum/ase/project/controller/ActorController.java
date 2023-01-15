package edu.tum.ase.project.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/{actorType}")  // possible "client", "dispatcher", "dispatcher" or "all-actors"
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

    @CrossOrigin
    @GetMapping("")
    public List<Actor> getAllActors(@PathVariable(value = "actorType") final String actorTypeStr) {
        /*
        Returns all the actors or all the actors of the specified type

        Usage:
        curl -X GET localhost:8080/all-actors  # returns all the actors
        curl -X GET localhost:8080/client  # returns all the clients
        curl -X GET localhost:8080/dispatcher  # returns all the dispatchers
        curl -X GET localhost:8080/dispatcher  # returns all the dispatchers

        Return value is the list of items (id, email, actorType)

        Example:
        >> curl -X GET localhost:8080/client
        << status code 200
           [{"id":"638d2a011064dc1a387acc8e","email":"babushka@gmail.ru","actorType":"client"},
            {"id":"638f0fceb39edb53d3f173d5","email":"disp@gmail.ru","actorType":"dispatcher"}]
        */
        if (Objects.equals(actorTypeStr, "all-actors")){
            return actorService.getAllActors();
        }
        return actorService.getAllActorsByType(str2actorType(actorTypeStr));
    }

    @CrossOrigin
    @PostMapping("")
    @ResponseBody
    public Actor createActor(@PathVariable(value = "actorType") final String actorTypeStr,
                             @RequestBody ObjectNode json) {
        /*
        Creates an actor item

        Usage:
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/client  # create a new client
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/dispatcher  # create a new dispatcher
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/dispatcher  # create a new dispatcher
        EMAIL and PASS are user strings

        Returns a created actor item, i.e. an object with fields (id, email, actorType)
        Fails if the new actor has an email which is already presented in the actor table.

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/client
        << status code 200
           {"id":"63bd25a327dcd14c6d30451c","email":"babushka@gmail.ru","actorType":"client"}

        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru", "pass": "p@ss"}' localhost:8080/client  # try to add a new actor with the same email
        << status code 500
           {"timestamp":"2023-01-10T09:20:25.186+00:00","status":500,"error":"Internal Server Error","path":"/client"}

        >> curl -i -X POST -H "Content-Type: application/json" -d '{"email": "fish@gmail.ru"}' localhost:8080/client  # no "pass" filed
        << status code 400
           {"timestamp":"2023-01-10T08:48:32.783+00:00","status":400,"error":"Bad Request","path":"/client"}
        */
        String email;
        String pass;
        try {
            email = json.get("email").asText();
            pass = json.get("pass").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        return actorService.createActor(email, pass, str2actorType(actorTypeStr));
    }


    @CrossOrigin
    @PostMapping("/email")
    public Optional<Actor> getActorByEmail(@PathVariable(value = "actorType") final String actorTypeStr,
                                           @RequestBody ObjectNode json) {
        /*
        Returns an actor by email

        Usage
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/client/email  # find a client
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/dispatcher/email  # find a dispatcher
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/dispatcher/email  # find a dispatcher
        EMAIL is a user string

        Returns the actor item, i.e. an object with fields (id, email, actorType) or null

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru"}' localhost:8080/client/email
        << status code 200
           {"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"client"}
        */
        String email;
        try {
            email = json.get("email").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        return actorService.findByActorTypeAndEmail(email, str2actorType(actorTypeStr));
    }

    @CrossOrigin
    @DeleteMapping("")
    public void deleteActorById(@RequestBody ObjectNode json, @PathVariable String actorType){
        /*
        Deletes an actor by id

        Usage
        curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/client  # delete a client
        curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/dispatcher  # delete a dispatcher
        curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/dispatcher  # delete a dispatcher
        ACTOR_ID is a String representing Id of an object from the actor database

        Returns only status code (or an error)

        Example:
        >> curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "63bd3438e03f596350f8afb5"}' localhost:8080/client
        << status code 200

        >> curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "63bd3438e03f596350f8afb5"}' localhost:8080/client  # try to delete a key missing from the table
        << status code 406
           {"timestamp":"2023-01-10T09:48:24.762+00:00","status":406,"error":"Not Acceptable","path":"/client"}
        */
        String actorId;
        try {
            actorId = json.get("actorId").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        try {
            actorService.deleteActor(actorId);
            return;
        } catch (ObjectDoesNotExist e) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
