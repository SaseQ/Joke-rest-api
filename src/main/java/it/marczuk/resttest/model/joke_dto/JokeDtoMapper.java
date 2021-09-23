package it.marczuk.resttest.model.joke_dto;

import it.marczuk.resttest.model.Joke;

public class JokeDtoMapper {

    private JokeDtoMapper() {
    }

    public static Joke mapToJoke(JokeDto jokeDto) {
        return mapJokeDtoToJoke(jokeDto);
    }

    private static Joke mapJokeDtoToJoke(JokeDto jokeDto) {
        Joke joke = new Joke();
        joke.setId(jokeDto.getId());
        joke.setValue(jokeDto.getValue());
        joke.setCategories(jokeDto.getCategories());
        joke.setVersion(jokeDto.getVersion());

        return joke;
    }
}
