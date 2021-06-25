package it.marczuk.resttest.service;

import com.google.common.cache.CacheStats;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface JokeService {

    Joke getRandomJoke();

    Joke getRandomJokeByCategory(String category) throws ExecutionException;

    List<Joke> getJokeByQuery(String query);

//    Joke getRandomJokeByCategory(Category category);
}
