package edu.tum.ase.project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class PropertiesReader {
    // inspired by https://stackoverflow.com/questions/38281512/how-to-read-data-from-java-properties-file-using-spring-boot
    @Autowired
    private Environment env;

    public String getProperty(String pPropertyKey) {
        return env.getProperty(pPropertyKey);
    }
}
