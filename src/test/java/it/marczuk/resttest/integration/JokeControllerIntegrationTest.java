package it.marczuk.resttest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.marczuk.resttest.controller.JokeController;
import it.marczuk.resttest.model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Objects;

import static it.marczuk.resttest.config.integration_config.TestDataIntegration.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JokeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JokeController jokeController;

    @Test
    void shouldReturn404WhenGetJokeById() throws Exception {
        //given
        final String TEST_ID = "abcd5"; //form testDatabaseConfig

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/joke/database/id/" + TEST_ID))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn();

//        String actual = mvcResult.getResolvedException().getMessage();
        String actual = Objects.requireNonNull(mvcResult.getResolvedException()).getMessage();

        //then
        assertEquals("Could not find joke by id: " + TEST_ID, actual);
    }

    @Test
    void shouldReturnCorrectValueWhenGetJokeById() throws Exception {
        //given
        final String TEST_ID = "abcd1";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/joke/database/id/" + TEST_ID))
                .andExpect(status().isOk()).andReturn();

        Joke joke = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Joke.class);

        //then
        assertEquals(TEST_ID, joke.getId());
        assertEquals("Chuck Norris doesn't need to use AJAX because pages are too afraid to postback anyways.", joke.getValue());
        assertEquals("dev", joke.getCategories().get(0));
    }

    @Test
    void shouldAddNewJoke() throws Exception {
        //given
        Joke joke = createTestJoke();

        //when
        mockMvc.perform(post("/api/v1/joke/database/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(joke))
        )
                .andExpect(status().is(HttpStatus.CREATED.value())).andReturn();
    }

    @Test
    void shouldReturnJokeAndCheckJokeValueIsString() {
        //when
        Joke actual = jokeController.getRandomJoke();

        //then
        assertFalse(actual.getValue().isEmpty());
        assertEquals(actual.getValue().getClass(), String.class);
    }

    @Test
    void shouldReturnJokeByCategoryAndCheckJokeValueIsString() {
        //when
        Joke actual = jokeController.getRandomJokeByCategory("animal");

        //then
        assertFalse(actual.getValue().isEmpty());
        assertEquals(actual.getValue().getClass(), String.class);
        assertEquals("animal", actual.getCategories().get(0));
    }

    @Test
    void shouldReturnJokeQueryAndCheckJokeValueIsString() {
        //when
        List<Joke> actual = jokeController.getJokesByQuery("dog");

        //then
        assertFalse(actual.isEmpty());
        actual.forEach(j -> assertEquals(j.getValue().getClass(), String.class));
    }
}
