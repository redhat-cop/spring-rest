package com.hello;

import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Document
public class Greeting {

    @Id
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
