package it.marczuk.resttest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Joke {

    @Id
    private String id;
    private String value;
    private List<String> categories;

    @Version
    private Long version;

}
