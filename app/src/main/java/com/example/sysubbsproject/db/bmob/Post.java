package com.example.sysubbsproject.db.bmob;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Post extends BmobObject {

    private String PostTitle;
    private String PostText;
    private Person userPtr;
    private String authorName,authorObjectId,authorProfile; // 牺牲一点数据库冗余度
    private List<String> imgs;
    private String labels;
    private int likes = 0;
    private int comment = 0;
    private int collection = 0;

    public String getTitle() {
        return this.PostTitle;
    }

    public void setTitle(String title) {
        this.PostTitle = title;
    }

    public String getContent() {
        return this.PostText;
    }

    public void setContent(String content) {
        this.PostText = content;
    }

    public Person getAuthor() {
        return this.userPtr;
    }
    public String getAuthorName(){
        return this.authorName;
    }
    public void setAuthorProfile(String authorProfile){
        this.authorProfile = authorProfile;
    }
    public String getAuthorProfile(){
        return this.authorProfile;
    }

    public void setAuthorName(String author_name){
        this.authorName = author_name;
    }

    public String getAuthorObjectId(){
        return this.authorObjectId;
    }
    public void setAuthorObjectId(String objectId){
        this.authorObjectId = objectId;
    }

    public void setAuthor(Person author) {
        this.userPtr = author;
        this.authorName = author.getUsername();
        this.authorProfile = author.getProfile();
    }

    public List<String> getImageUrls() {
        return imgs;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imgs = imageUrls;
    }

    public void setLabel(String label){
        this.labels = label;
    }

    public String getLabel(){
        return this.labels;
    }

    public int getNumLikes() {
        return this.likes;
    }

    public void setNumLikes(boolean ifSet) {
        if(ifSet){
            this.likes += 1;
        }
        else{
            this.likes -= 1;
        }
    }

    public void setNumLikes(int numLikes){
        this.likes = numLikes;
    }
    public int getNumComments() {
        return this.comment;
    }

    public void setNumComments() {
        this.comment += 1;
    }
    public void setNumComments(int numComments){
        this.comment = numComments;
    }

    public int getNumFavorites(){
        return this.collection;
    }
    public void setNumFavorites(boolean ifSet){
        if(ifSet){
            this.collection += 1;
        }
        else{
            this.collection -= 1;
        }
    }

    public void setNumFavorites(int numFavorites){
        this.collection = numFavorites;
    }
    /*public Comment[] getComments(){
        return this.CommentsList;
    }*/
}
