package it.marczuk.resttest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class JokeQuery {

    private Integer total;
    private List<Joke> result = new ArrayList<>();
}
