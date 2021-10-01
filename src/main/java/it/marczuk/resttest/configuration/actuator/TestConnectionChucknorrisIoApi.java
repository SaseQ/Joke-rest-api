package it.marczuk.resttest.configuration.actuator;

import it.marczuk.resttest.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "test-connection-chucknorrisio-api")
public class TestConnectionChucknorrisIoApi {

    private static final String RANDOM_URL = "https://api.chucknorris.io/jokes/random";
    private final RestTemplate restTemplate;

    @Autowired
    public TestConnectionChucknorrisIoApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ReadOperation
    public Map<String, String> test() {
        Map<String, String> responseMap = new HashMap<>();

        try {
            restTemplate.getForObject(RANDOM_URL, Joke.class);
            responseMap.put("status", "ok");
        } catch (Exception e) {
            responseMap.put("status", "fail");
        }

        return responseMap;
    }
}
