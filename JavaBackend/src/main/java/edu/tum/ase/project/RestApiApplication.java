package edu.tum.ase.project;

import com.mongodb.client.MongoClient;
import edu.tum.ase.project.model.Actor;
import edu.tum.ase.project.service.ActorService;
import edu.tum.ase.project.service.OrderService;
import edu.tum.ase.project.utils.ActorType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = { RestApiApplication.class })
public class RestApiApplication implements CommandLineRunner {

	@Autowired
	MongoClient mongoClient;

	@Autowired
	ActorService actorService;

	@Autowired
	OrderService orderService;

	private static final String defaultDispatcherPass = "sathwik";
	private static final String defaultDispatcherEmail = "sathwik@gmail.ru";

	private static final Logger log = LoggerFactory.getLogger(RestApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("MongoClient = " + mongoClient.getClusterDescription());
	}
}