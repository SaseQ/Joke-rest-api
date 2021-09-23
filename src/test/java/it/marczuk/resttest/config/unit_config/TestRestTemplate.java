package it.marczuk.resttest.config.unit_config;

import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.JokeQuery;
import org.springframework.web.client.RestTemplate;

public abstract class TestRestTemplate {

    private static final String RANDOM_URL = "https://api.chucknorris.io/jokes/";

    public static Joke getRandomJokeFromRestTemplate(RestTemplate restTemplate) {
        return restTemplate.getForObject("https://api.chucknorris.io/jokes/random", Joke.class);
    }

    public static Joke getRandomJokeByCategoryFromRestTemplate(RestTemplate restTemplate) {
        return restTemplate.getForObject(RANDOM_URL + "random?category=" + "dev", Joke.class);
    }

    public static JokeQuery getRandomJokeQueryFromRestTemplate(RestTemplate restTemplate) {
        return restTemplate.getForObject(RANDOM_URL + "search?query=" + "dog", JokeQuery.class);
    }
}
