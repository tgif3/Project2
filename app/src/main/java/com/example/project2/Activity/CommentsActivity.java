package com.example.project2.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.project2.Adapters.CommentAdapter;
import com.example.project2.MessageController;
import com.example.project2.NotificationCenter;
import com.example.project2.R;
import com.example.project2.entity.Comment;
import com.example.project2.interfaces.CommentRepositoryObserver;
import com.example.project2.interfaces.Subject;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity implements CommentRepositoryObserver {
    private Context context;
    private MessageController messageController;
    private Subject notificationCenter;
    private CommentAdapter commentAdapter;

    private boolean gridView = false;
    private boolean endCreate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        context = CommentsActivity.this;
        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.commentRegisterObserver(this);
        messageController = MessageController.getInstance(context);

        initializeUI();
    }

    @SuppressLint("InflateParams")
    private void initializeUI() {
        messageController.fetchComments(isOnline(), messageController.getPostId());

        Button changeViewComments = findViewById(R.id.changeViewComments);
        if (gridView) {
            changeViewComments.setText(R.string.listView);
        } else {
            changeViewComments.setText(R.string.gridView);
        }
        changeViewComments.setOnClickListener(v -> {
            if (endCreate) {
                endCreate = false;
                if (gridView) {
                    gridView = false;
                    changeViewComments.setText(R.string.gridView);
                } else {
                    gridView = true;
                    changeViewComments.setText(R.string.listView);
                }
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

        LinearLayout linearLayout = findViewById(R.id.linearLayoutComment);
        linearLayout.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        AbsListView absListView;
        if (gridView) {
            absListView = (GridView) layoutInflater.inflate(R.layout.grid_view, null);
        } else {
            absListView = (ListView) layoutInflater.inflate(R.layout.list_view, null);
        }

        linearLayout.addView(absListView);
        commentAdapter = new CommentAdapter(context, null);
        absListView.setAdapter(commentAdapter);

        endCreate = true;
    }

    private void updateLinearLayout(ArrayList<Comment> arrayList) {
        runOnUiThread(() -> {
            if (arrayList != null && arrayList.size() > 0) {
                setTitle("Post " + messageController.getPostId() + ", Comments" + arrayList.size());
            } else {
                setTitle("Post " + messageController.getPostId());
            }
            commentAdapter.setComments(arrayList);
            commentAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationCenter.commentRemoveObserver(this);
    }

    @Override
    public void updateComments(ArrayList<Comment> arrayList) {
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
