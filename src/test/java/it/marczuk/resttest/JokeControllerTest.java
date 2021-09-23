package it.marczuk.resttest;

import it.marczuk.resttest.controller.JokeController;
import it.marczuk.resttest.model.Joke;
import it.marczuk.resttest.service.joke.JokeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(JokeController.class)
class JokeControllerTest {

    @MockBean
    private JokeService jokeService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnRandomJoke() throws Exception {
        when(jokeService.getRandomJoke())
                .thenReturn(new Joke());

        this.mockMvc.perform(get("/api/v1/joke"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(jokeService.getRandomJoke().getId()))
                .andExpect(jsonPath("$.value").value(jokeService.getRandomJoke().getValue()));
    }

    @Test
    void shouldReturnRandomJokeByCategory() throws Exception {
        when(jokeService.getRandomJokeByCategory("animal"))
                .thenReturn(new Joke());

        this.mockMvc.perform(get("/api/v1/joke/category/{category}", "animal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(jokeService.getRandomJokeByCategory("animal").getId()))
                .andExpect(jsonPath("$.value").value(jokeService.getRandomJokeByCategory("animal").getValue()));
    }

    @Test
    void shouldReturnRandomJokeByQuery() throws Exception {
        when(jokeService.getJokeByQuery("dog"))
                .thenReturn(List.of(new Joke()));

        this.mockMvc.perform(get("/api/v1/joke/query")
                .param("query", "dog"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
//                .andExpect(jsonPath("$.id").value(jokeService.getJokeByQuery("dog").get(0).getId()))
//                .andExpect(jsonPath("$.value").value(jokeService.getJokeByQuery("dog").get(0).getValue()));

    }

}
