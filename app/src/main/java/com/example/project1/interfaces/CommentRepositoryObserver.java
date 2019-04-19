package com.example.project1.interfaces;

import com.example.project1.entity.Comment;

import java.util.ArrayList;

public interface CommentRepositoryObserver {
    void updateComments(ArrayList<Comment> arrayList);
}
