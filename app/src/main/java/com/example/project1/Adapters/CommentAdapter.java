package com.example.project1.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.entity.Comment;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    final private Context mContext;
    private Comment[] comments;

    public CommentAdapter(Context context, Comment[] comments) {
        this.mContext = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Comment comment = comments[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.comment, null);
        }

        final TextView emailTextView = convertView.findViewById(R.id.email);
        final TextView nameTextView = convertView.findViewById(R.id.name);
        final TextView bodyTextView = convertView.findViewById(R.id.body);

        String email = comment.getEmail();
        String name = comment.getName();
        String body = comment.getBody();

        if (body.length() > 90) {
            body = body.substring(0, 90);
            if (body.contains(" ")) {
                body = body.substring(0, body.lastIndexOf(' ')) + " ...";
            } else {
                body = body + " ...";
            }
        }

        emailTextView.setText(email);
        nameTextView.setText(name);
        bodyTextView.setText(body);

        convertView.setBackgroundColor(Color.rgb(230, 230, 230));

        return convertView;
    }
}
