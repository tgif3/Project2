package com.example.project2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.entity.Post;

import java.util.ArrayList;
import java.util.Random;

public class PostAdapter extends BaseAdapter {

    final private Context mContext;
    private Post[] posts;
    private Random random = new Random();

    public PostAdapter(Context context, Post[] posts) {
        this.mContext = context;
        this.posts = posts;
    }

    public void setPosts(ArrayList<Post> arrayList) {
        Post[] posts = new Post[arrayList.size()];
        this.posts = arrayList.toArray(posts);
    }

    public Post[] getPosts() {
        return posts;
    }

    @Override
    public int getCount() {
        if (posts == null) {
            return 0;
        }
        return posts.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Post post = posts[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.post, null);
        }

        final TextView titleTextView = convertView.findViewById(R.id.title);
        final TextView bodyTextView = convertView.findViewById(R.id.body);

        String title = post.getTitle();
        String body = post.getBody();

        if (body.length() > 90) {
            body = body.substring(0, 90);
            if (body.contains(" ")) {
                body = body.substring(0, body.lastIndexOf(' ')) + " ...";
            } else {
                body = body + " ...";
            }
        }

        titleTextView.setText(title);
        bodyTextView.setText(body);

        convertView.setBackgroundColor(Color.rgb(
                random.nextInt(100) + 156,
                random.nextInt(100) + 156,
                random.nextInt(100) + 156));

        return convertView;
    }
}
