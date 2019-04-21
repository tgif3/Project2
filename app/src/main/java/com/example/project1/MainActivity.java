package com.example.project1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.project1.Adapters.PostAdapter;
import com.example.project1.entity.Post;
import com.example.project1.interfaces.PostRepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostRepositoryObserver {
    private Context context;
    private MessageController messageController;
    private Post[] posts;
    private PostAdapter postAdapter;

    private Subject notificationCenter;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.postRegisterObserver(this);

        messageController = MessageController.getInstance(context);

        initializeUI();
    }

    private void initializeUI() {
        messageController.fetchPosts(false);
    }

    private void updateLinearLayout(ArrayList<Post> arrayList) {
        runOnUiThread(() -> {
            posts = new Post[arrayList.size()];
            posts = arrayList.toArray(posts);

            gridView = findViewById(R.id.gridViewPost);
            postAdapter = new PostAdapter(context, posts);
            gridView.setAdapter(postAdapter);

            for (int i = 0; i < posts.length; i++) {
                Log.i("Test", posts[i].getId());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationCenter.postRemoveObserver(this);
    }

    @Override
    public void updatePosts(ArrayList<Post> arrayList) {
        updateLinearLayout(arrayList);
    }
}
