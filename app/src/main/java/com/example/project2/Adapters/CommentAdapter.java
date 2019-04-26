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
import com.example.project2.entity.Comment;

import java.util.ArrayList;
import java.util.Random;

public class CommentAdapter extends BaseAdapter {
    final private Context context;
    private Comment[] comments;
    private Random random = new Random();

    public CommentAdapter(Context context, Comment[] comments) {
        this.context = context;
        this.comments = comments;
    }

    public void setComments(ArrayList<Comment> arrayList) {
        Comment[] comments = new Comment[arrayList.size()];
        this.comments = arrayList.toArray(comments);
    }

    @Override
    public int getCount() {
        if (comments == null) {
            return 0;
        }
        return comments.length;
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
        final Comment comment = comments[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.comment, null);
        }

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

        return convertView;
    }
}
