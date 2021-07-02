package it.marczuk.resttest.service;

import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class DefaultJokeService implements JokeService {

    private static final String RANDOM_URL = "https://api.chucknorris.io/jokes/";
    private final RestTemplate restTemplate;

    @Autowired
    public DefaultJokeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    Supplier<Joke> memoizedSupplier = Suppliers.memoize(this::obtainRandomJoke);

    private final LoadingCache<String, Joke> cache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES) //- remove records that have been idle for 10 minutes
            //.expireAfterWrite(10, TimeUnit.MINUTES) //- remove records based on their total live time
            .maximumSize(3) //- limit the size of our cache
            .build(CacheLoader.from(this::obtainRandomJokeByCategory));

    private <T> T callGetMethod(String url, Class<T> responseType) {
        return restTemplate.getForObject(RANDOM_URL + url, responseType);
    }

    @Override
    public Joke getRandomJoke() {
        return memoizedSupplier.get();
    }

    private Joke obtainRandomJoke() {
        return callGetMethod("random", Joke.class);
    }

    @Override
    public Joke getRandomJokeByCategory(String category) {
        return cache.getUnchecked(category); //– this computes and loads the value into the cache if it doesn't already exist.
        //return cache.getIfPresent(category);
        //return cache.get(category);
    }

    private Joke obtainRandomJokeByCategory(String category) {
        return callGetMethod("random?category=" + category, Joke.class);
    }

    @Override
    public List<Joke> getJokeByQuery(String query) {
        JokeQuery jokeQuery = callGetMethod("search?query=" + query, JokeQuery.class);
        return jokeQuery != null ? jokeQuery.getResult() : Collections.emptyList();
    }

    //    implemetnacja cache'a dla categorii
    //    LoadingCache guava porownaj to z memoize
    //        restTemplate.

}
