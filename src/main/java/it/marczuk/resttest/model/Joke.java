package it.marczuk.resttest.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Joke {

    @Id
    private String id;
    private String value;

    @Version
    private Long version;

}
