package com.example.project1;

import android.content.Context;

import com.example.project1.entity.Post;

import java.util.ArrayList;

public class MessageController {
    private NotificationCenter notificationCenter;
    private ConnectionManager connectionManager;
    private StorageManager storageManager;
    private DBHelper dbHelper;
    private static MessageController INSTANCE;
    private ArrayList<Post> posts;
    private boolean connecting = false;


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

    public void fetch(boolean fromCache) {
        if (fromCache) {
            Thread storage = new Thread(() -> {
                posts = storageManager.load();
                notificationCenter.postsLoaded(posts);
            }, "storage");
            storage.start();
        }
        else {
            Thread cloud = new Thread(() -> {
                connecting = true;
                posts = connectionManager.load();
                storageManager.save(posts);
                notificationCenter.postsLoaded(posts);
                connecting = false;
            }, "cloud");
            cloud.start();
        }
    }

    public void clear() {
        posts.clear();
        notificationCenter.postsLoaded(posts);
    }

    public boolean isConnecting() {
        return connecting;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }
}
