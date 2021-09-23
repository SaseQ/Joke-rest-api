package it.marczuk.resttest.model.joke_dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class JokeDto {

    private String id;
    private String value;
    private List<String> categories;
    private Long version;
}
