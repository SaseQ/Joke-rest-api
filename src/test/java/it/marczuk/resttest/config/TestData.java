package it.marczuk.resttest.config;

import it.marczuk.resttest.model.Joke;

import java.util.List;

public class TestData {

    public List<Joke> createTestData() {
        Joke joke1 = new Joke();
        joke1.setId("1L");
        joke1.setValue("randomJoke1");
        joke1.setCategories(List.of("animal"));
        joke1.setVersion(0L);

        Joke joke2 = new Joke();
        joke2.setId("2L");
        joke2.setValue("randomJoke2");
        joke2.setCategories(List.of("dev"));
        joke2.setVersion(0L);

        return List.of(joke1, joke2);
    }
}
