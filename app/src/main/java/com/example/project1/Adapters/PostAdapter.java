package com.example.project1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.entity.Post;

public class PostAdapter extends BaseAdapter {

    final private Context mContext;
    private Post[] posts;

    public PostAdapter(Context context, Post[] posts) {
        this.mContext = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Post book = posts[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.post, null);
        }

        final TextView titleTextView = convertView.findViewById(R.id.title);
        final TextView bodyTextView = convertView.findViewById(R.id.body);

        String title = book.getTitle();
        String body = book.getBody();

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

        convertView.setBackgroundColor(Color.rgb(230, 230, 230));

        return convertView;
    }
}
