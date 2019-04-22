package com.example.project1.interfaces;

import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;

import java.util.ArrayList;

public interface Subject {
    void postRegisterObserver(PostRepositoryObserver postRepositoryObserver);
    void postRemoveObserver(PostRepositoryObserver postRepositoryObserver);
    void postsLoaded(ArrayList<Post> arrayList);
    void commentRegisterObserver(CommentRepositoryObserver postRepositoryObserver);
    void commentRemoveObserver(CommentRepositoryObserver postRepositoryObserver);
    void commentsLoaded(ArrayList<Comment> arrayList);
}
