package it.marczuk.resttest.service.joke;

import it.marczuk.resttest.exception.BadRequestToRestTemplateException;
import it.marczuk.resttest.exception.CategoryNotFoundException;
import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.JokeQuery;
import it.marczuk.resttest.repository.CategoryRepository;
import it.marczuk.resttest.repository.JokeRepository;
import it.marczuk.resttest.service.db.DatabaseJokeDao;
import it.marczuk.resttest.service.db.DatabaseJokeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static it.marczuk.resttest.config.unit_config.TestData.*;
import static it.marczuk.resttest.config.unit_config.TestRestTemplate.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultJokeServiceTest {

    private DefaultJokeService defaultJokeService;
    private RestTemplate restTemplate;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        //mock
        restTemplate = mock(RestTemplate.class);
        categoryRepository = mock(CategoryRepository.class);
        JokeRepository jokeRepository = mock(JokeRepository.class);

        //Create instance defaultJokeService
        DatabaseJokeService databaseJokeService = new DatabaseJokeService(jokeRepository, categoryRepository);
        DatabaseJokeDao databaseJokeDao = new DatabaseJokeDao(databaseJokeService);
        defaultJokeService = new DefaultJokeService(restTemplate, databaseJokeDao);
    }

    @Test
    void shouldReturnRandomJokeFromRestTemplate() {
        //given
        Joke testJoke = getFirstTestJoke();

        //when
        when(getRandomJokeFromRestTemplate(restTemplate)).thenReturn(testJoke);
//        when(restTemplate.getForObject("https://api.chucknorris.io/jokes/random", Joke.class)).thenReturn(testJoke);
//        doReturn(testJoke).when(restTemplate).getForObject(RANDOM_URL + "random", Joke.class);

        Joke actual = defaultJokeService.getRandomJoke();

        //then
        assertNotNull(actual);
        assertEquals("1L", actual.getId());
        assertEquals("randomJoke1", actual.getValue());
        assertEquals("animal", getCategoryStringFromActualJoke(actual));
        assertEquals(0L, actual.getVersion());
    }

    @Test
    void shouldReturnBadRequestExceptionIfRandomJokeIsNull() {
        //when
        when(getRandomJokeFromRestTemplate(restTemplate)).thenReturn(null);

        //then
        assertThrows(BadRequestToRestTemplateException.class,
                () -> defaultJokeService.getRandomJoke(),
                "Bad request to rest template: https://api.chucknorris.io/jokes/random"
        );
    }

    @Test
    void shouldReturnRandomJokeByCategoryFromRestTemplate() {
        //given
        List<Category> testCategoryList = getTestCategoriesList();
        Joke testJoke = getSecondTestJoke();

        //when
        when(categoryRepository.findAll()).thenReturn(testCategoryList);
        when(getRandomJokeByCategoryFromRestTemplate(restTemplate)).thenReturn(testJoke);

        Joke actual = defaultJokeService.getRandomJokeByCategory("dev");

        //then
        assertNotNull(actual);
        assertEquals("dev", getCategoryStringFromActualJoke(actual));
    }

    @Test
    void shouldReturnCategoryNotFoundExceptionIfNotFoundCategoryInCategoryList() {
        //given
        List<Category> testCategoryList = getTestCategoriesList();

        //when
        when(categoryRepository.findAll()).thenReturn(testCategoryList);

        //then
        assertThrows(CategoryNotFoundException.class,
                    () -> defaultJokeService.getRandomJokeByCategory("error"),
                    "Could not find category: error"
                );
    }

    @Test
    void shouldReturnRandomJokeByQueryFromRestTemplate() {
        //given
        JokeQuery testJokeQuery = getTestJokeQuery();

        //when
        when(getRandomJokeQueryFromRestTemplate(restTemplate)).thenReturn(testJokeQuery);

        List<Joke> actual = defaultJokeService.getJokeByQuery("dog");

        //then
        assertNotNull(actual);
        assertEquals("animal", getCategoryStringFromActualJokeList(actual));
        assertEquals(getTestJokesList(), actual);
    }
}
