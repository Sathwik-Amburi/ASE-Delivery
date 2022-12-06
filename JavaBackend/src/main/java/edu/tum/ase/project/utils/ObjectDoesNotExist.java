package edu.tum.ase.project.utils;

public class ObjectDoesNotExist extends Exception{
    public ObjectDoesNotExist() {
    }

    public ObjectDoesNotExist(String message) {
        super(message);
    }
}
