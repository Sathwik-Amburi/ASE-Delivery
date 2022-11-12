package com.example.javabackend.model;



import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
public class Project {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String name;

    protected Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
