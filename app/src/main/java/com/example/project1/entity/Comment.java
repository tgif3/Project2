package com.example.project1.entity;

public class Comment {
    private String id;
    private String postId;
    private String name;
    private String email;
    private String body;


    public Comment(String id, String postId, String name, String email, String body) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
