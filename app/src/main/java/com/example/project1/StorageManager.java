package com.example.project1;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.project1.entity.Post;

import java.util.ArrayList;

public class StorageManager {
    private static final String KEY = "MANAGER";
    private static final String LAST_KEY = "LAST_KEY";
    private static StorageManager INSTANCE;
    private StorageManager() {}

    public static StorageManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StorageManager();
        }
        return INSTANCE;
    }

    public void save(ArrayList<Post> posts) {
        DBHelper dbHelper = MessageController.getInstance().getDbHelper();
        for (Post post : posts) {
            dbHelper.insertPost(post);
        }
    }

    public ArrayList<Post> load() {
        return MessageController.getInstance().getDbHelper().getAllPosts();
    }
}
