package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.repository.ActorRepository;
import edu.tum.ase.project.utils.ActorType;
import edu.tum.ase.project.utils.WrongObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(String email, String pass, ActorType actorType) {
        Actor newActor = new Actor(email, pass, actorType);
        return actorRepository.save(newActor);
    }

    public Optional<Actor> findByActorTypeAndEmail(String email, ActorType actorType) {
        return actorRepository.findByActorTypeAndEmail(actorType, email);
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public List<Actor> getAllActorsByType(ActorType actorType) {
        return actorRepository.findAllByActorType(actorType);
    }

    public void deleteActor(String ActorId) throws WrongObject {
        Optional<Actor> actor = actorRepository.findById(ActorId);
        if (actor.isEmpty()){
            throw new WrongObject(String.format("An actor with id '%s' does not exist", ActorId));
        }
        actorRepository.delete(actor.get());
        return;
    }
}
