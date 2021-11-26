package com.example.sysubbsproject.db.bmob;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

    private String comment;
    private Person user1,user2;
    private String postId;
    private boolean toPost;

    public String getContent() {
        return this.comment;
    }

    public void setContent(String content) {
        this.comment = content;
    }

    public void setPostUser(Person person){
        this.user1 = person;
    }

    public Person getPostUser() {
        return user1;
    }

    public void setCommentUser(Person person){
        this.user2 = person;
    }

    public Person getCommentUser(){
        return user2;
    }

    public void setToPost(boolean toPost){
        this.toPost = toPost;
    }

    public boolean getToPost(){
        return this.toPost;
    }

    public Comment setPost(Post post) {
        this.postId = post.getObjectId();
        return this;
    }

}