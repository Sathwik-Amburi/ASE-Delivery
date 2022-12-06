package edu.tum.ase.project.model;

import com.mongodb.lang.NonNull;
import edu.tum.ase.project.utils.ActorType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actors")
public class Actor {
    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String email;

    @NonNull
    private String pass;

    @NonNull
    private final ActorType actorType;

    public Actor(String email, String pass, ActorType actorType) {
        this.email = email;
        this.pass = pass;
        this.actorType = actorType;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public ActorType getActorType() {
        return this.actorType;
    }

    public boolean comparePass(String pass) {
        return pass.equals(this.pass);
    }
}
