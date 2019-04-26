package com.example.project2.interfaces;

import com.example.project2.entity.Comment;

import java.util.ArrayList;

public interface CommentRepositoryObserver {
    void updateComments(ArrayList<Comment> arrayList);
}
