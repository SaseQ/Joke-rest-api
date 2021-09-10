package it.marczuk.resttest.controller;

import it.marczuk.resttest.exception.JokeNotFoundExeption;
import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.service.db.DatabaseJokeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return databaseJokeService.findJokeById(id).orElseThrow(() -> new JokeNotFoundExeption("Could not find joke by id: " + id));
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return databaseJokeService.findAllCategories();
    }

    @GetMapping("/categories/id/{id}")
    public Category getCategoryById(String id) {
        return databaseJokeService.findCategoryById(id).orElseThrow(() -> new JokeNotFoundExeption("Could not find category by id: " + id));
    }

    @PostMapping("/add")
    public ResponseEntity<Joke> addNewJoke(@RequestBody Joke joke) {
        databaseJokeService.saveJoke(joke);
        return ResponseEntity.status(201).body(joke);
    }
}
