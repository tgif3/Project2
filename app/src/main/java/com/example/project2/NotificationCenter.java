package com.example.project2;

import com.example.project2.entity.Comment;
import com.example.project2.entity.Post;
import com.example.project2.interfaces.CommentRepositoryObserver;
import com.example.project2.interfaces.PostRepositoryObserver;
import com.example.project2.interfaces.Subject;

import java.util.ArrayList;


public class NotificationCenter implements Subject {
    private static NotificationCenter INSTANCE = null;

    private ArrayList<PostRepositoryObserver> postRepositoryObservers;
    private ArrayList<CommentRepositoryObserver> commentRepositoryObservers;

    private ArrayList<Comment> comments = new ArrayList<>();

    private NotificationCenter() {
        postRepositoryObservers = new ArrayList<>();
        commentRepositoryObservers = new ArrayList<>();
    }

    public static NotificationCenter getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NotificationCenter();
        }
        return INSTANCE;
    }

    @Override
    public void postRegisterObserver(PostRepositoryObserver postRepositoryObserver) {
        if(!postRepositoryObservers.contains(postRepositoryObserver)) {
            postRepositoryObservers.add(postRepositoryObserver);
        }
    }

    @Override
    public void postRemoveObserver(PostRepositoryObserver postRepositoryObserver) {
        postRepositoryObservers.remove(postRepositoryObserver);
    }

    @Override
    public void postsLoaded(ArrayList<Post> arrayList) {
        for (PostRepositoryObserver observer: postRepositoryObservers) {
            observer.updatePosts(arrayList);
        }
    }

    @Override
    public void commentRegisterObserver(CommentRepositoryObserver commentRepositoryObserver) {
        if(!commentRepositoryObservers.contains(commentRepositoryObserver)) {
            commentRepositoryObservers.add(commentRepositoryObserver);
        }
    }

    @Override
    public void commentRemoveObserver(CommentRepositoryObserver commentRepositoryObserver) {
        commentRepositoryObservers.remove(commentRepositoryObserver);
    }

    @Override
    public void commentsLoaded(ArrayList<Comment> arrayList) {
        this.comments = arrayList;
        for (CommentRepositoryObserver observer: commentRepositoryObservers) {
            observer.updateComments(arrayList);
        }
    }
}
