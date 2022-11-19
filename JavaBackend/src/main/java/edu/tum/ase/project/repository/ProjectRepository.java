package edu.tum.ase.project.repository;

import edu.tum.ase.project.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

// The MongoRepository is typed to the Document, and the type of the Document's ID
public interface ProjectRepository extends MongoRepository<Project, String> {
    // TODO: Write a function to find the project by Name (Am I right?)
    Project findByName(String name);// Looks like 'save' is a default method of Spring service
    // TODO replace with 'Optional<Project> Project findByName(String name);'
}