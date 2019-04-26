package com.example.project1.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project1.NotificationCenter;
import com.example.project1.R;
import com.example.project1.entity.Comment;
import com.example.project1.interfaces.CommentRepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;
import java.util.Random;

public class CommentsActivity extends AppCompatActivity implements CommentRepositoryObserver {
    private Context context;

    private Subject notificationCenter;

    private LinearLayout linearLayout;

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        random = new Random();

        context = CommentsActivity.this;

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.commentRegisterObserver(this);

        linearLayout = findViewById(R.id.linearLayout);
    }

    private void updateLinearLayout(ArrayList<Comment> arrayList) {
        runOnUiThread(() -> {
            linearLayout.removeAllViews();
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
        notificationCenter.commentRemoveObserver(this);
    }

    @Override
    public void updateComments(ArrayList<Comment> arrayList) {
        updateLinearLayout(arrayList);
    }
}
