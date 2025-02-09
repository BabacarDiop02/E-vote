package sn.forcen.java.groupe1.sousgroupe2.evoteapispring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EVoteApiSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EVoteApiSpringApplication.class, args);
    }
}
