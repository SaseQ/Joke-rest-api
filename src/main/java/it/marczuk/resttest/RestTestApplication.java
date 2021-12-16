package it.marczuk.resttest;

import it.marczuk.resttest.model.user.User;
import it.marczuk.resttest.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class RestTestApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestTestApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
        userService.saveUser(new User("John", "john@gmail.com", "1234"));
        userService.saveUser(new User("Saseq", "saseq.pl@gmail.com", "qwert123"));

        userService.addRoleToUser("john@gmail.com", "user");
        userService.addRoleToUser("saseq.pl@gmail.com", "admin");
        };
    }
}
