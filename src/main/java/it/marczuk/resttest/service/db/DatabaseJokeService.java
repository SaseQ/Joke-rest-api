package it.marczuk.resttest.service.db;

import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.repository.CategoryRepository;
import it.marczuk.resttest.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseJokeService {

    private final JokeRepository jokeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DatabaseJokeService(JokeRepository jokeRepository, CategoryRepository categoryRepository) {
        this.jokeRepository = jokeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Joke> findAllJokes() {
        return jokeRepository.findAll();
    }

    public Optional<Joke> findJokeById(String id) {
        return jokeRepository.findById(id);
    }

    public List<Joke> findJokesByCategories(List<String> categories) {
        return jokeRepository.findAllByCategories(categories);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    public void saveJoke(Joke joke) {
        jokeRepository.save(joke);
    }

    public void saveAllCategories(List<Category> categoryList) {
        categoryRepository.saveAll(categoryList);
    }

    public void deleteAllCategories(List<Category> categoryList) {
        categoryRepository.deleteAll(categoryList);
    }
}
