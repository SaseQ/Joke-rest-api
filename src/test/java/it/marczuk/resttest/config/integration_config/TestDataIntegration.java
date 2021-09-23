package it.marczuk.resttest.config.integration_config;

import it.marczuk.resttest.model.Joke;

import java.util.List;

public abstract class TestDataIntegration {

    public static Joke createTestJoke() {
        Joke joke = new Joke();
        joke.setId("abcd3");
        joke.setValue("Chuck Norris did in fact, build Rome in a day.");
        joke.setCategories(List.of("travel"));

        return joke;
    }
}
