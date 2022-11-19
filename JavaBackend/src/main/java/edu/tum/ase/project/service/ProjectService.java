package edu.tum.ase.project.service;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
        // TODO: implement (Am I right?)
        return projectRepository.save(project);  // Looks like 'save' is a default method of Spring service
    }

    public Project findByName(String name) {
        // TODO: implement (Am I right?)
        return projectRepository.findByName(name);
    }

    public List<Project> getAllProjects() {
        // TODO: implement (Am I right?)
        return projectRepository.findAll();
    }
}
