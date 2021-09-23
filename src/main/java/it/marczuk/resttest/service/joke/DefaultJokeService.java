package it.marczuk.resttest.service.joke;

import it.marczuk.resttest.exception.BadRequestToRestTemplateException;
import it.marczuk.resttest.exception.CategoryNotFoundException;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.JokeQuery;
import it.marczuk.resttest.service.db.DatabaseJokeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class DefaultJokeService implements JokeService {

    private static final String RANDOM_URL = "https://api.chucknorris.io/jokes/";
    private final RestTemplate restTemplate;
    private final DatabaseJokeDao databaseJokeDao;

    @Autowired
    public DefaultJokeService(RestTemplate restTemplate, DatabaseJokeDao databaseJokeDao) {
        this.restTemplate = restTemplate;
        this.databaseJokeDao = databaseJokeDao;
    }

    @Override
    public Joke getRandomJoke() {
        return databaseJokeDao.saveJokeIfNotExist(callGetMethod("random", Joke.class));
    }

    @Override
    public Joke getRandomJokeByCategory(String category) {
        if(databaseJokeDao.isItCategory(category)) {
            return databaseJokeDao.saveJokeIfNotExist(callGetMethod("random?category=" + category, Joke.class));
        }
        throw new CategoryNotFoundException("Could not find category: " + category);
    }

    @Override
    public List<Joke> getJokeByQuery(String query) {
        JokeQuery jokeQuery = callGetMethod("search?query=" + query, JokeQuery.class);
        return jokeQuery != null ? jokeQuery.getResult() : Collections.emptyList();
    }

    public <T> T callGetMethod(String url, Class<T> responseType) {
        Optional<T> response = ofNullable(restTemplate.getForObject(RANDOM_URL + url, responseType));
        return response.orElseThrow(() -> new BadRequestToRestTemplateException("Bad request to rest template: " + RANDOM_URL + url));
    }
}
