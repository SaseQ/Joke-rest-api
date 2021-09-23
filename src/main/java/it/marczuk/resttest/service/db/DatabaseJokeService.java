package it.marczuk.resttest.service.db;

import it.marczuk.resttest.exception.CategoryNotFoundException;
import it.marczuk.resttest.exception.JokeNotFoundException;
import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.joke_dto.JokeDto;
import it.marczuk.resttest.repository.CategoryRepository;
import it.marczuk.resttest.repository.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static it.marczuk.resttest.model.joke_dto.JokeDtoMapper.*;

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

    public Joke findJokeById(String id) {
        return jokeRepository.findById(id).orElseThrow(() -> new JokeNotFoundException("Could not find joke by id: " + id));
    }

    public Optional<Joke> findJokeByIdOptional(String id) {
        return jokeRepository.findById(id);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Could not find category by id: " + id));
    }

    public void saveJoke(Joke joke) {
        jokeRepository.save(joke);
    }

    public Joke saveJokeInJokeDto(JokeDto jokeDto) {
        Joke joke = mapToJoke(jokeDto);
        jokeRepository.save(mapToJoke(jokeDto));
        return joke;
    }

    public void saveAllCategories(List<Category> categoryList) {
        categoryRepository.saveAll(categoryList);
    }

    public void deleteAllCategories(List<Category> categoryList) {
        categoryRepository.deleteAll(categoryList);
    }
}
