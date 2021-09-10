package it.marczuk.resttest.configuration;

import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.repository.CategoryRepository;
import it.marczuk.resttest.repository.JokeRepository;
import it.marczuk.resttest.service.categories.CategoriesJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration
public class TestDatabaseConfig {

    @Value("${activeProfile.test}")
    private boolean activeTestProfile;

    private final JokeRepository jokeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoriesJokeService categoriesJokeService;

    @Autowired
    public TestDatabaseConfig(JokeRepository jokeRepository, CategoryRepository categoryRepository, CategoriesJokeService categoriesJokeService) {
        this.jokeRepository = jokeRepository;
        this.categoryRepository = categoryRepository;
        this.categoriesJokeService = categoriesJokeService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if(activeTestProfile) {
            jokeRepository.deleteAll();
            categoryRepository.deleteAll();

            categoriesJokeService.getAllJokeCategories();

            Joke joke1 = new Joke();
            joke1.setId("abcd0");
            joke1.setValue("They say curiosity killed the cat. This is false. Chuck Norris killed the cat. Every single one of them.");
            joke1.setCategories(List.of("animal"));

            Joke joke2 = new Joke();
            joke2.setId("abcd1");
            joke2.setValue("Chuck Norris doesn't need to use AJAX because pages are too afraid to postback anyways.");
            joke2.setCategories(List.of("dev"));

            Joke joke3 = new Joke();
            joke3.setId("abcd2");
            joke3.setValue("Chuck Norris can drink an entire gallon of milk in thirty-seven seconds.");
            joke3.setCategories(List.of("food"));

            jokeRepository.saveAll(List.of(joke1, joke2, joke3));
        }
    }
}
