
package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/allProjects")
    public List<Project> getAllBoxes() {
        // TODO: Implement (Am I right?)
        return projectService.getAllProjects();
    }
    // TODO replace with the following:
//    @GetMapping("/allProjects")
//    public ResponseEntity<List<Project>> getAllBoxes() {
//        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
//    }

    // TODO: Implement a POST Endpoint to create a project with a given name (Am I right?)
    @PostMapping
    public Project createBox(@RequestBody String projectName) {
        Project newProject = new Project(projectName);
        return projectService.createProject(newProject);
    }


    // TODO: Implement an Endpoint to find a project with a given name (Am I right?)
    @GetMapping("/{projectName}")
    public Project getBoxById(@PathVariable(value = "projectName") final String boxId) {
        return projectService.findByName(boxId);
    }

}