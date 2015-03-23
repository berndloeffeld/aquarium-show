package de.aquariumshow.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.aquariumshow")
public class Application {

    public static void main(String[] args) {
	String webPort = System.getenv("PORT");
    	if (webPort == null || webPort.isEmpty()) {
        	webPort = "8080";
    	}
    	System.setProperty("server.port", webPort);
    	SpringApplication.run(Application.class, args);
    }
}
