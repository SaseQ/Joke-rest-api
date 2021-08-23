package it.marczuk.resttest.service;

import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.repository.CategoryRepository;
import it.marczuk.resttest.repository.JokeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DatabaseJokeDao {

    private final JokeRepository jokeRepository;
    private final CategoryRepository categoryRepository;
    private final Logger log = LoggerFactory.getLogger(DatabaseJokeDao.class);

    @Autowired
    public DatabaseJokeDao(JokeRepository jokeRepository, CategoryRepository categoryRepository) {
        this.jokeRepository = jokeRepository;
        this.categoryRepository = categoryRepository;
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

    @Transactional
    public void addCategory(List<Category> categories) {
        List<Category> all = categoryRepository.findAll();
        if(!all.equals(categories)) {
            categoryRepository.deleteAll();
            categoryRepository.saveAll(categories);
        }
    }
    
    public boolean isItCategory(String category) {
        List<Category> all = categoryRepository.findAll();
        for(Category x: all) {
            if(x.getCategory().equals(category)){
                return true;
            }
        }
        return false;
    }
}
