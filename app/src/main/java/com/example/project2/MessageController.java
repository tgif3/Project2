package com.example.project2;

import android.content.Context;
import com.example.project2.entity.Comment;
import com.example.project2.entity.Post;

import java.util.ArrayList;
import java.util.Date;

public class MessageController {
    private NotificationCenter notificationCenter;
    private ConnectionManager connectionManager;
    private StorageManager storageManager;
    private DBHelper dbHelper;
    private static MessageController INSTANCE;

    private ArrayList<Post> posts;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int postId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

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

    public void fetchPosts(boolean isOnline) {
        boolean fromCache = false;

        if (!isOnline) {
            fromCache = true;
        } else {
            String time = dbHelper.getStoreTime("posts");
            if (!time.equals("")) {
                long currentTime = new Date().getTime();
                if (currentTime - Long.valueOf(time) < 300000) {
                    fromCache = true;
                }
            }
        }

        if (fromCache) {
            Thread storage = new Thread(() -> {
                posts = storageManager.loadPosts();
                notificationCenter.postsLoaded(posts);
            }, "storage");
            storage.start();
        }
        else {
            Thread cloud = new Thread(() -> {
                posts = connectionManager.loadPost();
                storageManager.savePosts(posts);
                notificationCenter.postsLoaded(posts);
            }, "cloud");
            cloud.start();
        }
    }

    public void fetchComments(boolean isOnline, int postId) {
        boolean fromCache = false;

        String time = dbHelper.getStoreTime("comment" + postId);

        if (!isOnline) {
            fromCache = true;
        } else {
            if (!time.equals("")) {
                long currentTime = new Date().getTime();
                if (currentTime - Long.valueOf(time) < 300000) {
                    fromCache = true;
                }
            }
        }

        if (fromCache) {
            Thread storage = new Thread(() -> {
                comments.clear();
                comments = storageManager.loadComments(postId);
                notificationCenter.commentsLoaded(comments);
            }, "storage");
            storage.start();
        }
        else {
            Thread cloud = new Thread(() -> {
                comments = connectionManager.loadComment(postId);
                storageManager.saveComments(comments);
                notificationCenter.commentsLoaded(comments);
            }, "cloud");
            cloud.start();
        }
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }
}
