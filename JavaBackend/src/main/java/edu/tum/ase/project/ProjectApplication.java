package edu.tum.ase.project;

import com.mongodb.client.MongoClient;
import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.repository.ProjectRepository;
import edu.tum.ase.project.service.ActorService;
import edu.tum.ase.project.utils.ActorType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = { ProjectRepository.class })
public class ProjectApplication implements CommandLineRunner {

	@Autowired
	MongoClient mongoClient;

	@Autowired
	ActorService clientService;

	private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("MongoClient = " + mongoClient.getClusterDescription());
		List<Actor> clientList = clientService.getAllActorsByType(ActorType.Client);
		for (Actor client : clientList){
			log.info(client.toString());
		}
		log.info("Number of Clients in Database is " + clientList.size());


//		String projectName = "ASE Delivery";
//
//		Project project = projectService.createProject(new Project(projectName));
//
//		log.info(String.format("Project %s is created with id %s",
//				project.getName(),
//				project.getId()));
//
//		Project aseDeliveryProject = projectService.findByName(projectName);
//
//		log.info(String.format("Found Project %s with id %s",
//				aseDeliveryProject.getName(),
//				aseDeliveryProject.getId()));
//
//		List<Project> projectList = projectService.getAllProjects();
//		log.info("Number of Project in Database is " + projectList.size());
	}
}