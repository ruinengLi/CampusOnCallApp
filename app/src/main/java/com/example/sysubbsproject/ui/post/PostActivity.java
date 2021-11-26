package com.example.sysubbsproject.ui.post;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_navigation_post);
    }

    public static void savePost(Post post, String title, String content, Context context) {


        post.setTitle(title);
        post.setContent(content);
        //添加一对一关联，用户关联帖子
        post.setAuthor(BmobUser.getCurrentUser(Person.class));
        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "发布帖子成功", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}

