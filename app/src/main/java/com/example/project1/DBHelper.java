package com.example.project1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project1.entity.Comment;
import com.example.project1.entity.Post;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name) {
        super(context, name , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Posts(id INT NOT NULL PRIMARY KEY," +
                        " userId INT NOT NULL, title VARCHAR(255) NOT NULL, body text NOT NULL);"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Comments(id INT NOT NULL PRIMARY KEY," +
                        " postId INT NOT NULL, name VARCHAR(255) NOT NULL," +
                        " email VARCHAR(63) NOT NULL, body text NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Posts");
        db.execSQL("DROP TABLE IF EXISTS Comments");
        onCreate(db);
    }

    public void insertPost (Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", post.getId());
        contentValues.put("userId", post.getUserId());
        contentValues.put("title", post.getTitle());
        contentValues.put("body", post.getBody());
        db.insert("Posts", null, contentValues);
    }

    public void insertComment (Comment comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", comment.getId());
        contentValues.put("postId", comment.getPostId());
        contentValues.put("name", comment.getName());
        contentValues.put("email", comment.getEmail());
        contentValues.put("body", comment.getBody());
        db.insert("Comments", null, contentValues);
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res =  db.rawQuery( "select * from Posts", null);
        res.moveToFirst();

        while(!res.isAfterLast()) {
            Post post = new Post(res.getString(res.getColumnIndex("id")),
                    res.getString(res.getColumnIndex("userId")),
                    res.getString(res.getColumnIndex("title")),
                    res.getString(res.getColumnIndex("body")));

            arrayList.add(post);
            res.moveToNext();
        }

        return arrayList;
    }

    public ArrayList<Comment> getAllComments(int postId) {
        ArrayList<Comment> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res =  db.rawQuery(
                "select * from Comments where id="+postId+"", null);
        res.moveToFirst();

        while(!res.isAfterLast()) {
            Comment comment = new Comment(res.getString(res.getColumnIndex("id")),
                    res.getString(res.getColumnIndex("postId")),
                    res.getString(res.getColumnIndex("name")),
                    res.getString(res.getColumnIndex("email")),
                    res.getString(res.getColumnIndex("body")));

            arrayList.add(comment);
            res.moveToNext();
        }

        return arrayList;
    }
}
