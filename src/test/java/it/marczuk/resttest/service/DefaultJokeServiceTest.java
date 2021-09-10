package it.marczuk.resttest.service;

import it.marczuk.resttest.config.TestData;
import it.marczuk.resttest.model.Category;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.model.JokeQuery;
import it.marczuk.resttest.repository.CategoryRepository;
import it.marczuk.resttest.repository.JokeRepository;
import it.marczuk.resttest.service.db.DatabaseJokeDao;
import it.marczuk.resttest.service.db.DatabaseJokeService;
import it.marczuk.resttest.service.joke.DefaultJokeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultJokeServiceTest {

    private DefaultJokeService defaultJokeService;
    private RestTemplate restTemplate;
    private CategoryRepository categoryRepository;
    private List<Joke> dataList;

    private static final String RANDOM_URL = "https://api.chucknorris.io/jokes/";
    private static final String CATEGORY = "dev";

    @BeforeEach
    private void init(){
        restTemplate = mock(RestTemplate.class);
        categoryRepository = mock(CategoryRepository.class);
        JokeRepository jokeRepository = mock(JokeRepository.class);

        DatabaseJokeService databaseJokeService = new DatabaseJokeService(jokeRepository, categoryRepository);
        DatabaseJokeDao databaseJokeDao = new DatabaseJokeDao(databaseJokeService);
        defaultJokeService = new DefaultJokeService(restTemplate, databaseJokeDao);

        TestData testData = new TestData();
        dataList = testData.createTestData();
    }

    @Test
    void shouldReturnRandomJokeFromRestTemplate() {
        doReturn(dataList.get(0)).when(restTemplate).getForObject(RANDOM_URL + "random", Joke.class);
//        when(restTemplate.getForObject("https://api.chucknorris.io/jokes/random", Joke.class)).thenReturn(dataList.get(0));

        Joke actual = defaultJokeService.getRandomJoke();

        assertEquals("1L", actual.getId());
        assertEquals("randomJoke1", actual.getValue());
        assertEquals("animal", actual.getCategories().get(0));
        assertEquals(0L, actual.getVersion());
    }

    @Test
    void shouldReturnRandomJokeByCategoryFromRestTemplate() {
        doReturn(List.of(new Category(CATEGORY))).when(categoryRepository).findAll();
        doReturn(dataList.get(1)).when(restTemplate).getForObject(RANDOM_URL + "random?category=" + CATEGORY, Joke.class);

        Joke actual = defaultJokeService.getRandomJokeByCategory("dev");

        assertEquals("dev", actual.getCategories().get(0));
    }

    @Test
    void shouldReturnRandomJokeByQueryFromRestTemplate() {
        doReturn(new JokeQuery(1, dataList)).when(restTemplate).getForObject(RANDOM_URL + "search?query=dog", JokeQuery.class);

        List<Joke> actual = defaultJokeService.getJokeByQuery("dog");

        assertEquals("animal", actual.get(0).getCategories().get(0));
        assertEquals(dataList, actual);
    }

}
