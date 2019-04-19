package com.example.project1.interfaces;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public interface CommentRepositoryObserver {
    void updateComments(ArrayList<Comment> arrayList);
}
