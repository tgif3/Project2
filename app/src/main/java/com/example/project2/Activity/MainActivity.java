package com.example.project2.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.project2.Adapters.PostAdapter;
import com.example.project2.MessageController;
import com.example.project2.NotificationCenter;
import com.example.project2.R;
import com.example.project2.entity.Post;
import com.example.project2.interfaces.PostRepositoryObserver;
import com.example.project2.interfaces.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostRepositoryObserver {
    private Context context;
    private MessageController messageController;
    private Subject notificationCenter;
    private PostAdapter postAdapter;

    private boolean gridView = true;
    private boolean endCreate = false;

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

    @SuppressLint("InflateParams")
    private void initializeUI() {
        messageController.fetchPosts(isOnline());

        Button changeViewPost = findViewById(R.id.changeViewPost);
        changeViewPost.setOnClickListener(v -> {
            if (endCreate) {
                endCreate = false;
                if (gridView) {
                    gridView = false;
                    changeViewPost.setText(R.string.gridView);
                } else {
                    gridView = true;
                    changeViewPost.setText(R.string.listView);
                }
                initializeUI();
            }
        });

        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(v -> {
            if (endCreate) {
                endCreate = false;
                messageController.getDbHelper().drop();
                initializeUI();
            }
        });

        Button refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(v -> {
            if (endCreate) {
                endCreate = false;
                initializeUI();
            }
        });

        LinearLayout linearLayout = findViewById(R.id.linearLayoutPost);
        linearLayout.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AbsListView absListView;
        if (gridView) {
            absListView = (GridView) layoutInflater.inflate(R.layout.grid_view, null);
        } else {
            absListView = (ListView) layoutInflater.inflate(R.layout.list_view, null);
        }

        linearLayout.addView(absListView);
        postAdapter = new PostAdapter(context, null);
        absListView.setAdapter(postAdapter);

        absListView.setOnItemClickListener((parent, view, position, id) -> {
            messageController.setPostId(Integer.parseInt(postAdapter.getPosts()[position].getId()));
            Intent intent = new Intent(context, CommentsActivity.class).
                    putExtra("post id", postAdapter.getPosts()[position].getId());
            startActivity(intent);
        });

        endCreate = true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.members_menu, menu);
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
