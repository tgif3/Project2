package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.project1.Adapters.PostAdapter;
import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;
import com.example.project1.interfaces.PostRepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;
import java.util.zip.Inflater;

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
        messageController.fetchPosts(false);
        GridView gridView = findViewById(R.id.gridViewPost);
        postAdapter = new PostAdapter(context, null);
        gridView.setAdapter(postAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            messageController.setPostId(position);
            messageController.fetchComments(false, position);

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
