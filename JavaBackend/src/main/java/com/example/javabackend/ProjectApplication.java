package com.example.javabackend;

import com.example.javabackend.model.Project;
import com.example.javabackend.service.ProjectService;
import com.mongodb.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);
    @Autowired
    MongoClient mongoClient;
    @Autowired
    ProjectService projectService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("MongoClient = " + mongoClient.getClusterDescription());

        String projectName = "ASE Delivery";


        Project project = projectService.createProject(new Project(projectName));

        log.info(String.format("Project %s is created with id %s",
                project.getName(),
                project.getId()));

        Project aseDeliveryProject = projectService.findByName(projectName);

        log.info(String.format("Found Project %s with id %s",
                project.getName(),
                project.getId()));

        List<Project> projectList = projectService.getAllProjects();
        log.info("Number of Project in Database is " + projectList.size());
    }
}


