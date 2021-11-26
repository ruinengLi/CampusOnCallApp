package com.example.sysubbsproject.db;

import android.util.Log;

import com.example.sysubbsproject.db.bmob.Comment;
import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentHandler {
    public static void CreateComment(String content, Person user1, Person user2, Post post, SaveListener<String> listener) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostUser(user1);
        comment.setCommentUser(user2);
        comment.setPost(post);

        post.increment("numComments");
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.e("CreateComment", e.toString());
                }
            }
        });

        comment.save(listener);
    }

    public static void GetComments(Post post, int limit, int skip, FindListener<Comment> listener) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("post", post.getObjectId()).setLimit(limit).setSkip(skip).order("-createdAt");

        query.findObjects(listener);
    }
}
