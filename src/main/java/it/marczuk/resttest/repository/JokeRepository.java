package it.marczuk.resttest.repository;

import it.marczuk.resttest.model.Joke;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends MongoRepository<Joke, String> {

    List<Joke> findAllByCategories(List<String> categories);
}
