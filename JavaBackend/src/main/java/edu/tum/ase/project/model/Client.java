package edu.tum.ase.project.model;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class Client {
    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String email;

    @NonNull
    private String pass;

    public Client(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail(){
        return this.email;
    }

    public boolean comparePass(String pass){
        return pass.equals(this.pass);
    }
}
