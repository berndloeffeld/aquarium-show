package de.aquariumshow.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("de.aquariumshow")
@EntityScan("de.aquariumshow.model")
@EnableJpaRepositories("de.aquariumshow.repositories")
public class Application {

	static Logger log = LoggerFactory.getLogger(Application.class);
	
    public static void main(String[] args) {
	String webPort = System.getenv("PORT");
    	if (webPort == null || webPort.isEmpty()) {
        	webPort = "8080";
    	}
    	System.setProperty("server.port", webPort);
    	SpringApplication.run(Application.class, args);
    }
}
