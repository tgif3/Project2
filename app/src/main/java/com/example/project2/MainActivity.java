package com.example.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project2.api.Comment;
import com.example.project2.api.CommentGetter;
import com.example.project2.api.Post;
import com.example.project2.api.PostGetter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);


        new PostGetter() {
            @Override
            public void beat(List<Post> body) {
                Toast.makeText(getApplicationContext(), body.get(0).getBody(), Toast.LENGTH_SHORT).show();
                PostAdapter postAdapter = new PostAdapter(getApplicationContext(), body);
                listView.setAdapter(postAdapter);
            }

            @Override
            public void fail() {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        }.applyAsync();
    }
}
