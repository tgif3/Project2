package com.example.project1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project1.entity.Post;
import com.example.project1.interfaces.PostRepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostRepositoryObserver {
    private Context context;
    private LinearLayout linearLayout;
    private MessageController messageController;

    private Subject notificationCenter;

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
        linearLayout = findViewById(R.id.linear_layout);

        Button clearBtn = findViewById(R.id.clear_btn);
        clearBtn.setOnClickListener(v -> messageController.clear());

        Button refreshBtn = findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(v -> messageController.fetch(true));

        Button getBtn = findViewById(R.id.get_btn);
        getBtn.setOnClickListener(v -> {
            if (!messageController.isConnecting()) {
                messageController.fetch(false);
            }
        });
    }

    private void updateLinearLayout(ArrayList<Post> arrayList) {
        runOnUiThread(() -> {
            linearLayout.removeAllViews();
            for (Post post : arrayList) {
                TextView textView = new TextView(context);
                textView.setTextSize(30);
                textView.setTextColor(Color.rgb(0, 150, 0));
                textView.setText(post.getId());
                linearLayout.addView(textView);
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
