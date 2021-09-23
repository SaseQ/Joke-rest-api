package it.marczuk.resttest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Category {

    @Id
    private String id;
    private String value;

    public Category(String category) {
        this.value = category;
    }
}
