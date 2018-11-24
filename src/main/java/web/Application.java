package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main drive class which starts the spring boot website application.
 * 1. gradle build
 * 2. java -jar build/libs/gs-spring-boot-0.1.0.jar
 * 3. http://localhost:8080/
 */
@ComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
