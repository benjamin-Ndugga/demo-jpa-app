package com.example;

import com.example.entities.Person;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.repositories.PersonRepository;

@SpringBootApplication
public class DemoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAppApplication.class, args);
    }

    //load some dummy data
    @Bean
    public CommandLineRunner commandLineRunner(PersonRepository personRepository) {
        return (String[] args) -> {
            Stream.of(new Person("Gordon", "Kampala")).forEach(personRepository::save);
        };
    }
}
