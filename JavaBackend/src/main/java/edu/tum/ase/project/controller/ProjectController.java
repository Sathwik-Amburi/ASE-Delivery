
package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("")
    public List<Project> getAllProject() {
        // TODO: Implement
        return null;
    }

    // TODO: Implement a POST Endpoint to create a project with a given name

    // TODO: Implement an Endpoint to find a project with a given name

}