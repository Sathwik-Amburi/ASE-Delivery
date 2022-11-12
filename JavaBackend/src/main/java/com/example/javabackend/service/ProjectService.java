package com.example.javabackend.service;

import com.example.javabackend.model.Project;
import com.example.javabackend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
        // TODO: implement
        return null;
    }

    public Project findByName(String name) {
        // TODO: implement
        return null;
    }

    public List<Project> getAllProjects() {
        // TODO: implement
        return new ArrayList<Project>();
    }
}
