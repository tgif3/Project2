package com.example.project1;

import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;

import java.util.ArrayList;
import java.util.Date;

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
        dbHelper.insertStoreTime("posts", String.valueOf(new Date().getTime()));
    }

    public ArrayList<Post> loadPosts() {
        return MessageController.getInstance().getDbHelper().getAllPosts();
    }

    public void saveComments(ArrayList<Comment> comments) {
        DBHelper dbHelper = MessageController.getInstance().getDbHelper();
        for (Comment comment : comments) {
            dbHelper.insertComment(comment);
        }
        if (comments.size() > 0) {
            Comment comment = comments.get(0);
            dbHelper.insertStoreTime("comment" + comment.getPostId(), String.valueOf(new Date().getTime()));
        }
    }

    public ArrayList<Comment> loadComments(int postId) {
        return MessageController.getInstance().getDbHelper().getCommentsByPostId(postId);

    }
}
