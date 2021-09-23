package it.marczuk.resttest.config.unit_config;

import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.JokeQuery;

import java.util.List;

public abstract class TestData {

    private static final List<Joke> jokeList;
    private static final List<Category> categoryList;

    static {
//Create jokes and jokesList
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

        jokeList = List.of(joke1, joke2);

//Create categories and categoriesList
        Category category1 = new Category();
        category1.setId("1C");
        category1.setValue("animal");

        Category category2 = new Category();
        category2.setId("2C");
        category2.setValue("dev");

        categoryList = List.of(category1, category2);
    }

//Joke Methods
    public static Joke getFirstTestJoke() {
        return jokeList.get(0);
    }

    public static Joke getSecondTestJoke() {
        return jokeList.get(1);
    }

    public static List<Joke> getTestJokesList() {
        return jokeList;
    }

//JokeQuery Methods
    public static JokeQuery getTestJokeQuery() {
        JokeQuery jokeQuery = new JokeQuery();
        jokeQuery.setTotal(2);
        jokeQuery.setResult(getTestJokesList());
        return jokeQuery;
    }

//Category Methods
    public static List<Category> getTestCategoriesList(){
        return categoryList;
    }

    public static String getCategoryStringFromActualJoke(Joke actual) {
        return actual.getCategories().get(0);
    }

    public static String getCategoryStringFromActualJokeList(List<Joke> actual) {
        return actual.get(0).getCategories().get(0);
    }

}
