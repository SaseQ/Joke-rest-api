package it.marczuk.resttest.service.joke;

import it.marczuk.resttest.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static it.marczuk.resttest.config.unit_config.TestData.*;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.ONE_SECOND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheJokeServiceTest {

    private CacheJokeService cacheJokeService;

    @BeforeEach
    void setUp() {
        cacheJokeService = mock(CacheJokeService.class);
    }

    @Test
    void shouldReturnCachedRandomJoke(){
        //when
        when(cacheJokeService.getRandomJoke()).thenReturn(getFirstTestJoke());

        Joke firstTestRandomJoke = cacheJokeService.getRandomJoke();
        await().atMost(ONE_SECOND);
        Joke secondTestRandomJoke = cacheJokeService.getRandomJoke();

        //then
        assertEquals(firstTestRandomJoke, secondTestRandomJoke);
    }

    @Test
    void shouldReturnCachedRandomJokeByCategory() {
        //when
        when(cacheJokeService.getRandomJokeByCategory("dev")).thenReturn(getSecondTestJoke());

        Joke firstTestRandomJoke = cacheJokeService.getRandomJokeByCategory("dev");
        await().atMost(ONE_SECOND);
        Joke secondTestRandomJoke = cacheJokeService.getRandomJokeByCategory("dev");

        //then
        assertEquals(firstTestRandomJoke, secondTestRandomJoke);
    }

    @Test
    void shouldReturnCachedRandomJokeQuery() {
        //when
        when(cacheJokeService.getJokeByQuery("dog")).thenReturn(getTestJokesList());

        List<Joke> firstTestRandomJokeList = cacheJokeService.getJokeByQuery("dog");
        await().atMost(ONE_SECOND);
        List<Joke> secondTestRandomJokeList = cacheJokeService.getJokeByQuery("dog");

        //then
        assertEquals(firstTestRandomJokeList, secondTestRandomJokeList);
    }
}
