package it.marczuk.resttest.service;

import java.util.List;

public interface JokeService {

    Joke getRandomJoke();

    Joke getRandomJokeByCategory(String category);

    List<Joke> getJokeByQuery(String query);

    Joke getJokeById(String id);

}
