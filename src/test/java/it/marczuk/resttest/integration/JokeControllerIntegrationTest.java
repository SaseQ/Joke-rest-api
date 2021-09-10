package it.marczuk.resttest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.marczuk.resttest.model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JokeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturn404WhenGetJokeById() throws Exception {
        String TEST_ID = "abcd5";

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/joke/database/id/" + TEST_ID))
                .andExpect(status().is(404)).andReturn();
        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("Could not find joke by id: " + TEST_ID, actual);
    }

    @Test
    void shouldReturnCorrectValueWhenGetJokeById() throws Exception {
        String TEST_ID = "abcd1";

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/joke/database/id/" + TEST_ID))
                .andExpect(status().is(200)).andReturn();
        Joke joke = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Joke.class);

        assertEquals(TEST_ID, joke.getId());
        assertEquals("Chuck Norris doesn't need to use AJAX because pages are too afraid to postback anyways.", joke.getValue());
        assertEquals("dev", joke.getCategories().get(0));
    }

    @Test
    void shouldAddNewJoke() throws Exception {
        Joke joke = new Joke();
        joke.setId("abcd3");
        joke.setValue("Chuck Norris did in fact, build Rome in a day.");
        joke.setCategories(List.of("travel"));

        mockMvc.perform(post("/api/v1/joke/database/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(joke))
        )
                .andExpect(status().is(201)).andReturn();
    }
}
