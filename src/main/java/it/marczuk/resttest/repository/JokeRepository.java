package it.marczuk.resttest.repository;

import it.marczuk.resttest.model.Joke;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeRepository extends MongoRepository<Joke, String> {
}
