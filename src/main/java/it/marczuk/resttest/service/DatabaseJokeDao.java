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
import java.util.stream.Collectors;

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

//    @Transactional
//    public void addCategory(List<Category> categories) {
//        List<Category> all = categoryRepository.findAll();
//        if(!all.equals(categories)) {
//            categoryRepository.deleteAll();
//            categoryRepository.saveAll(categories);
//        }
//    }

//    @Transactional
//    public void addCategory(List<Category> categories) {
//        List<Category> all = categoryRepository.findAll();
//        categories.forEach(e -> {
//            if(!all.contains(e)) {
//                categoryRepository.save(e);
//            }
//        }); //if in database not exist categories element that adds to it
//        all.forEach(e -> {
//            if(!categories.contains(e)) {
//                categoryRepository.delete(e);
//            }
//        }); //if in categories not exist element but exist in database, it is removed
//    }

    @Transactional
    public void addCategory(List<Category> restCategories) {
        List<Category> databaseCategories = categoryRepository.findAll();
        categoryRepository.saveAll(getNewCategories(databaseCategories, restCategories));
        categoryRepository.deleteAll(getOldCategories(databaseCategories, restCategories));
    }
    
    public boolean isItCategory(String category) {
        List<Category> databaseCategories = categoryRepository.findAll();
        for(Category x: databaseCategories) {
            if(x.getCategory().equals(category)){
                return true;
            }
        }
        return false;
    }

    private List<Category> getNewCategories(List<Category> databaseCategories, List<Category> restCategories) {
        return restCategories.stream().filter(e -> !databaseCategories.contains(e)).collect(Collectors.toList());
    }

    private List<Category> getOldCategories(List<Category> databaseCategories, List<Category> restCategories) {
        return databaseCategories.stream().filter(e -> !restCategories.contains(e)).collect(Collectors.toList());
    }
}
