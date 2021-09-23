package it.marczuk.resttest.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JokeQuery {

    private Integer total;
    private List<Joke> result = new ArrayList<>();
}
