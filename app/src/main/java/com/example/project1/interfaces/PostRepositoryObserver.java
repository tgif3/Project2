package com.example.project1.interfaces;

import com.example.project1.entity.Post;

import java.util.ArrayList;

public interface PostRepositoryObserver {
    void updatePosts(ArrayList<Post> arrayList);
}
