package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.utils.ActorType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends MongoRepository<Actor, String> {
    Optional<Actor> findByActorTypeAndEmail(ActorType actorType, String email);

    List<Actor> findAllByActorType(ActorType actorType);
}