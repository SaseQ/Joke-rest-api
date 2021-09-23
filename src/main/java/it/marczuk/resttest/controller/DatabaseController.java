package it.marczuk.resttest.controller;

import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.joke_dto.JokeDto;
import it.marczuk.resttest.service.db.DatabaseJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/joke/database")
public class DatabaseController {

    private final DatabaseJokeService databaseJokeService;

    @Autowired
    public DatabaseController(DatabaseJokeService databaseJokeService) {
        this.databaseJokeService = databaseJokeService;
    }

    @GetMapping("/all")
    public List<Joke> getAllJokes() {
        return databaseJokeService.findAllJokes();
    }

    @GetMapping("/id/{id}")
    public Joke getJokeById(@PathVariable String id) {
        return databaseJokeService.findJokeById(id);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return databaseJokeService.findAllCategories();
    }

    @GetMapping("/categories/id/{id}")
    public Category getCategoryById(@PathVariable String id) {
        return databaseJokeService.findCategoryById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Joke> addNewJoke(@RequestBody JokeDto jokeDto) {
        Joke joke = databaseJokeService.saveJokeInJokeDto(jokeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(joke);
    }
}
