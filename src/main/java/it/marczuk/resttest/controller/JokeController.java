package it.marczuk.resttest.controller;

import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.service.joke.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/joke")
public class JokeController {

    private final JokeService jokeService;

    @Autowired
    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public Joke getRandomJoke() {
        return jokeService.getRandomJoke();
    }

    @GetMapping("/category/{category}")
    public Joke getRandomJokeByCategory(@PathVariable String category) {
        return jokeService.getRandomJokeByCategory(category);
    }

    @GetMapping("/query")
    public List<Joke> getJokesByQuery(@RequestParam(required = false) String query) {
        return jokeService.getJokeByQuery(query);
    }
}
