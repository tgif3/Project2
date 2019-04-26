package com.example.project2.interfaces;

import com.example.project2.entity.Post;

import java.util.ArrayList;

public interface PostRepositoryObserver {
    void updatePosts(ArrayList<Post> arrayList);
}
