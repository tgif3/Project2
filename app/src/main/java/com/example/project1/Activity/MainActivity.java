package com.example.project1.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.project1.Adapters.PostAdapter;
import com.example.project1.MessageController;
import com.example.project1.NotificationCenter;
import com.example.project1.R;
import com.example.project1.entity.Post;
import com.example.project1.interfaces.PostRepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostRepositoryObserver {
    private Context context;
    private MessageController messageController;

    private Subject notificationCenter;

    private PostAdapter postAdapter;

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
        messageController.fetchPosts();
        GridView gridView = findViewById(R.id.gridViewPost);
        postAdapter = new PostAdapter(context, null);
        gridView.setAdapter(postAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            messageController.fetchComments(Integer.parseInt(postAdapter.getPosts()[position].getId()));

            Intent intent = new Intent(context, CommentsActivity.class);
            startActivity(intent);
        });
    }

    private void updateLinearLayout(ArrayList<Post> arrayList) {
        runOnUiThread(() -> {
            postAdapter.setPosts(arrayList);
            postAdapter.notifyDataSetChanged();
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
