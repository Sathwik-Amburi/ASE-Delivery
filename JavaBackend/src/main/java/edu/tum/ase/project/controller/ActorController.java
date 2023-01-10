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
        /*
        Returns all the actors or all the actors of the specified type

        Usage:
        curl -X GET localhost:8080/AllActors/listAll  # returns all the actors
        curl -X GET localhost:8080/Client/listAll  # returns all the Clients
        curl -X GET localhost:8080/Dispatcher/listAll  # returns all the Dispatchers
        curl -X GET localhost:8080/Deliverer/listAll  # returns all the Deliverers

        Return value is the list of items (id, email, actorType)

        Example:
        >> curl -X GET localhost:8080/Client/listAll
        << status code 200
           [{"id":"638d2a011064dc1a387acc8e","email":"babushka@gmail.ru","actorType":"Client"},
            {"id":"638f0fceb39edb53d3f173d5","email":"disp@gmail.ru","actorType":"Dispatcher"}]
        */
        if (Objects.equals(actorTypeStr, "AllActors")){
            return actorService.getAllActors();
        }
        return actorService.getAllActorsByType(str2actorType(actorTypeStr));
    }

    @PostMapping("/create")
    @ResponseBody
    public Actor createActor(@PathVariable(value = "actorType") final String actorTypeStr,
                             @RequestBody ObjectNode json) {
        /*
        Creates an actor item

        Usage:
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/Client/create  # create a new Client
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/Dispatcher/create  # create a new Dispatcher
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>, "pass": <PASS>}' localhost:8080/Deliverer/create  # create a new Deliverer
        EMAIL and PASS are user strings

        Returns a created actor item, i.e. an object with fields (id, email, actorType)

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru", "pass": "p@ssw0rd"}' localhost:8080/Client/create
        << status code 200
           {"id":"63bd25a327dcd14c6d30451c","email":"babushka@gmail.ru","actorType":"Client"}

        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru", "pass": "p@ss"}' localhost:8080/Client/create  # try to add a new actor with the same email
        << status code 500
           {"timestamp":"2023-01-10T09:20:25.186+00:00","status":500,"error":"Internal Server Error","path":"/Client/create"}

        >> curl -i -X POST -H "Content-Type: application/json" -d '{"email": "fish@gmail.ru"}' localhost:8080/Client/create  # no "pass" filed
        << status code 400
           {"timestamp":"2023-01-10T08:48:32.783+00:00","status":400,"error":"Bad Request","path":"/Client/create"}
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

    @PostMapping("/find")
    public Optional<Actor> getActorByEmail(@PathVariable(value = "actorType") final String actorTypeStr,
                                           @RequestBody ObjectNode json) {
        /*
        Returns an actor by email

        Usage
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/Client/find  # find a Client
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/Dispatcher/find  # find a Dispatcher
        curl -X POST -H "Content-Type: application/json" -d '{"email": <EMAIL>}' localhost:8080/Deliverer/find  # find a Deliverer
        EMAIL is a user string

        Returns the actor item, i.e. an object with fields (id, email, actorType) or null

        Example:
        >> curl -X POST -H "Content-Type: application/json" -d '{"email": "babushka@gmail.ru"}' localhost:8080/Client/find
        << status code 200
           {"id":"63bd2d47dea40908ea916896","email":"babushka@gmail.ru","actorType":"Client"}
        */
        String email;
        try {
            email = json.get("email").asText();
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Illegal request argument");
        }
        return actorService.findByActorTypeAndEmail(email, str2actorType(actorTypeStr));
    }

    @DeleteMapping("/delete")
    public void deleteActorById(@RequestBody ObjectNode json, @PathVariable String actorType){
        /*
        Deletes an actor by id

        Usage
        curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Client/delete  # delete a Client
        curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Dispatcher/delete  # delete a Dispatcher
        curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": <ACTOR_ID>}' localhost:8080/Deliverer/delete  # delete a Deliverer
        ACTOR_ID is a String representing Id of an object from the actor database

        Returns only status code (or an error)

        Example:
        >> curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "63bd3438e03f596350f8afb5"}' localhost:8080/Client/delete
        << status code 200

        >> curl -X DELETE -H "Content-Type: application/json" -d '{"actorId": "63bd3438e03f596350f8afb5"}' localhost:8080/Client/delete  # try to delete a key missing from the table
        << status code 406
           {"timestamp":"2023-01-10T09:48:24.762+00:00","status":406,"error":"Not Acceptable","path":"/Client/delete"}
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
