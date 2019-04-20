package com.example.project1;

import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;

import java.util.ArrayList;

public class StorageManager {
    private static StorageManager INSTANCE;
    private StorageManager() {}

    public static StorageManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StorageManager();
        }
        return INSTANCE;
    }

    public void savePosts(ArrayList<Post> posts) {
        DBHelper dbHelper = MessageController.getInstance().getDbHelper();
        for (Post post : posts) {
            dbHelper.insertPost(post);
        }
    }

    public ArrayList<Post> loadPosts() {
        return MessageController.getInstance().getDbHelper().getAllPosts();
    }

    public void saveComments(ArrayList<Comment> comments) {
        DBHelper dbHelper = MessageController.getInstance().getDbHelper();
        for (Comment comment : comments) {
            dbHelper.insertComment(comment);
        }
    }

    public ArrayList<Comment> loadComments(int postId) {
        return MessageController.getInstance().getDbHelper().getCommentsByPostId(postId);
    }
}
