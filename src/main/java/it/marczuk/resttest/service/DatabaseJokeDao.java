package it.marczuk.resttest.service;

import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.repository.JokeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseJokeDao {

    private final JokeRepository jokeRepository;
    private final Logger log = LoggerFactory.getLogger(DatabaseJokeDao.class);

    @Autowired
    public DatabaseJokeDao(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public Joke getJokeById(String id) {
        return jokeRepository.findById(id).orElseThrow(() -> new JokeNotFoundExeption("Could not find joke by id: " + id));
    }

    @Transactional
    public Joke databaseOperation(Joke joke) {
        if(jokeRepository.findById(joke.getId()).isEmpty()) {
            log.debug("Joke id: {} does not exist in the database", joke.getId());
            jokeRepository.save(joke);
        }
        return joke;
    }
}
