package com.example.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project2.api.Post;
import com.example.project2.api.PostGetter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new PostGetter() {
            @Override
            public void beat(List<Post> body) {

            }

            @Override
            public void fail() {

            }
        }.applyAsync();
    }
}
