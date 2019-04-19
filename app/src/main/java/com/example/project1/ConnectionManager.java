package com.example.project1;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private static ConnectionManager INSTANCE;
    private boolean end = false;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }

    public ArrayList<Post> loadPost() {
        end = false;

        ArrayList<Post> result = new ArrayList<>();

        AndroidNetworking.get("https://jsonplaceholder.typicode.com/posts")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Post.class, new ParsedRequestListener<List<Post>>() {
                    @Override
                    public void onResponse(List<Post> posts) {
                        result.addAll(posts);
                        Log.i("Posts", "posts size : " + posts.size());
                        for (Post post : posts) {
                            Log.i("Posts", "\n" + post.getId() + "\n" + post.getUserId() +
                                    "\n" + post.getTitle() + "\n" + post.getBody());
                        }

                        end = true;
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.i("Posts", "on Error");
                        end = true;
                    }
                });

        while (!end) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public ArrayList<Comment> loadComment(int postId) {
        end = false;

        ArrayList<Comment> result = new ArrayList<>();

        AndroidNetworking.get("https://jsonplaceholder.typicode.com/comments?postId={postId}")
                .addPathParameter("postId", String.valueOf(postId))
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Comment.class, new ParsedRequestListener<List<Comment>>() {
                    @Override
                    public void onResponse(List<Comment> comments) {
                        result.addAll(comments);
                        Log.i("Comments", "comments size : " + comments.size());
                        for (Comment comment: comments) {
                            Log.i("Comments", "\n" + comment.getId() + "\n" + comment.getPostId() +
                                    "\n" + comment.getEmail() + "\n" + comment.getName() + "\n" +
                                    comment.getBody());
                        }

                        end = true;
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.i("Comments", "on Error");
                        end = true;
                    }
                });

        while (!end) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


}
