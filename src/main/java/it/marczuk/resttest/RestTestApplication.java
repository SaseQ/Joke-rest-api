package it.marczuk.resttest;

import it.marczuk.resttest.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class RestTestApplication implements CommandLineRunner {

    @Autowired
    JokeService jokeService;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws ExecutionException {
        for(int i=0; i<5; i++) {
            System.out.println(jokeService.getRandomJoke());
        }
        System.out.println("-------------");

        for(int i=0; i<5; i++) {
            System.out.println(jokeService.getRandomJokeByCategory("animal"));
        }
        System.out.println("-------------");

        jokeService.getJokeByQuery("dog").forEach(System.out::println);
    }
}
