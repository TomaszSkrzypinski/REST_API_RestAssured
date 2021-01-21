package com.jsonplaceholder.model;

import lombok.Data;

@Data
public class Comment {

    private long id;
    private long postId;
    private String name;
    private String email;
    private String body;
}
