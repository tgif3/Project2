package com.example.project1;

import android.content.Context;

import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;

import java.util.ArrayList;

public class MessageController {
    private NotificationCenter notificationCenter;
    private ConnectionManager connectionManager;
    private StorageManager storageManager;
    private DBHelper dbHelper;
    private static MessageController INSTANCE;
    private ArrayList<Post> posts;
    private ArrayList<Comment> comments;

    private boolean connectingPost = false;
    private boolean connectingComment = false;

    private MessageController(Context context) {
        storageManager = StorageManager.getInstance();
        connectionManager = ConnectionManager.getInstance();
        notificationCenter = NotificationCenter.getInstance();
        posts = new ArrayList<>();
        dbHelper = new DBHelper(context, "my database");
    }

    public static MessageController getInstance() {
        return INSTANCE;
    }

    public static MessageController getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new MessageController(context);
        }
        return INSTANCE;
    }

    public void fetchPosts(boolean fromCache) {
        if (fromCache) {
            Thread storage = new Thread(() -> {
                posts = storageManager.loadPosts();
                notificationCenter.postsLoaded(posts);
            }, "storage");
            storage.start();
        }
        else {
            Thread cloud = new Thread(() -> {
                connectingPost = true;
                posts = connectionManager.loadPost();
                storageManager.savePosts(posts);
                notificationCenter.postsLoaded(posts);
                connectingPost = false;
            }, "cloud");
            cloud.start();
        }
    }

    public void fetchComments(boolean fromCache, int postId) {
        if (fromCache) {
            Thread storage = new Thread(() -> {
                comments = storageManager.loadComments(postId);
                notificationCenter.commentsLoaded(comments);
            }, "storage");
            storage.start();
        }
        else {
            Thread cloud = new Thread(() -> {
                connectingComment = true;
                comments = connectionManager.loadComment(postId);
                storageManager.saveComments(comments);
                notificationCenter.commentsLoaded(comments);
                connectingComment = false;
            }, "cloud");
            cloud.start();
        }
    }

    public void clear() {
        posts.clear();
        comments.clear();
        notificationCenter.postsLoaded(posts);
        notificationCenter.commentsLoaded(comments);
    }

    public boolean isConnectingPost() {
        return connectingPost;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public boolean isConnectingComment() {
        return connectingComment;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
