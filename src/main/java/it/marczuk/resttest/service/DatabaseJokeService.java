package it.marczuk.resttest.service;

import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseJokeService {

    private final JokeRepository jokeRepository;

    @Autowired
    public DatabaseJokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public Joke getJokeById(String id) {
        return jokeRepository.findById(id).orElseThrow(() -> new JokeNotFoundExeption("Could not find joke by id: " + id));
    }

    @Transactional
    public synchronized Joke databaseOperation(Joke joke) {
        if(jokeRepository.findById(joke.getId()).isEmpty()) {
            jokeRepository.save(joke);
        }
        return joke;
    }
}
