package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project1.Adapters.CommentAdapter;
import com.example.project1.Adapters.PostAdapter;
import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;
import com.example.project1.interfaces.CommentRepositoryObserver;
import com.example.project1.interfaces.PostRepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CommentsActivity extends AppCompatActivity implements CommentRepositoryObserver {
    private Context context;

    private Subject notificationCenter;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        context = CommentsActivity.this;

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.commentRegisterObserver(this);

        linearLayout = findViewById(R.id.linearLayout);
    }

    private void updateLinearLayout(ArrayList<Comment> arrayList) {
        runOnUiThread(() -> {
            linearLayout.removeAllViews();
            for (Comment comment : arrayList) {

                Log.i("Commentsss", comment.getBody());

                final LayoutInflater layoutInflater = LayoutInflater.from(context);
                View convertView = layoutInflater.inflate(R.layout.post, null);

                final TextView emailTextView = convertView.findViewById(R.id.email);
                final TextView nameTextView = convertView.findViewById(R.id.name);
                final TextView bodyTextView = convertView.findViewById(R.id.body);

                String email = comment.getEmail();
                String name = comment.getName();
                String body = comment.getBody();

                emailTextView.setText(email);
                nameTextView.setText(name);
                bodyTextView.setText(body);

                convertView.setBackgroundColor(Color.rgb(230, 230, 230));

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
