package com.example.javabackend;

import com.mongodb.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ProjectApplication.class);
    @Autowired
    MongoClient mongoClient;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("MongoClient = " + mongoClient.getClusterDescription());
    }
}
