package it.marczuk.resttest.service;

import it.marczuk.resttest.model.Joke;

import java.util.List;

public interface JokeService {

    Joke getRandomJoke();

    Joke getRandomJokeByCategory(String category);

    List<Joke> getJokeByQuery(String query);

}
