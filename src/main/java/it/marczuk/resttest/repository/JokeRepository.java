package it.marczuk.resttest.repository;

import it.marczuk.resttest.service.Joke;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JokeRepository extends MongoRepository<Joke, String> {
}
