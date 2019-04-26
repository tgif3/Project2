package com.example.project2.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project2.MessageController;
import com.example.project2.NotificationCenter;
import com.example.project2.R;
import com.example.project2.entity.Comment;
import com.example.project2.interfaces.CommentRepositoryObserver;
import com.example.project2.interfaces.Subject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CommentsActivity extends AppCompatActivity implements CommentRepositoryObserver {
    private Context context;

    private Subject notificationCenter;

    private LinearLayout linearLayout;

    private Random random;

    private MessageController messageController = MessageController.getInstance();
    private Integer postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setTitle("Post" + getIntent().getStringExtra("post id"));

        random = new Random();

        context = CommentsActivity.this;

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.commentRegisterObserver(this);

        linearLayout = findViewById(R.id.linearLayout);
        notificationCenter.commentsLoaded();
    }

    private void updateLinearLayout(ArrayList<Comment> arrayList) {
        runOnUiThread(() -> {
            linearLayout.removeAllViews();
            if (arrayList.size() > 0) {
                postId = Integer.valueOf(arrayList.get(0).getPostId());
                setTitle("Post" + arrayList.get(0).getPostId() + ", " + arrayList.size() + " Comments");
            }
            for (Comment comment : arrayList) {
                final LayoutInflater layoutInflater = LayoutInflater.from(context);
                View convertView = layoutInflater.inflate(R.layout.comment, null);

                final TextView emailTextView = convertView.findViewById(R.id.email);
                final TextView nameTextView = convertView.findViewById(R.id.name);
                final TextView bodyTextView = convertView.findViewById(R.id.body);

                String email = comment.getEmail();
                String name = comment.getName();
                String body = comment.getBody();

                emailTextView.setText(email);
                nameTextView.setText(name);
                bodyTextView.setText(body);

                convertView.setBackgroundColor(Color.rgb(
                        random.nextInt(100) + 156,
                        random.nextInt(100) + 156,
                        random.nextInt(100) + 156));

                linearLayout.addView(convertView);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageController.getComments().clear();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("Test", "ajab...");
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(postId);
        outState.putIntegerArrayList("copy", arrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        messageController.fetchComments(Objects.requireNonNull(savedInstanceState.getIntegerArrayList("copy")).get(0));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
