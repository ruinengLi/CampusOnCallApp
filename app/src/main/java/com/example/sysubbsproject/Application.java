package com.example.sysubbsproject;

import android.content.Context;

import com.example.sysubbsproject.db.bmob.Post;

import java.util.ArrayList;
import java.util.List;

public class Application extends android.app.Application {

    private static Context context;
    private static List<Post> treeholePosts,recruitPosts,exchangePosts,lostandfoundPosts,sharingPosts,courseCommentPosts;
    private static List<Post> allPosts,myPosts,myLikePosts,myFavoritePosts;

    @Override
    public void onCreate() {
        super.onCreate();

        myPosts = new ArrayList<Post>();
        courseCommentPosts = new ArrayList<Post>();
        sharingPosts = new ArrayList<Post>();
        lostandfoundPosts = new ArrayList<Post>();
        exchangePosts = new ArrayList<Post>();
        recruitPosts = new ArrayList<Post>();
        treeholePosts = new ArrayList<Post>();
        allPosts = new ArrayList<Post>();
        myLikePosts = new ArrayList<Post>();
        myFavoritePosts = new ArrayList<Post>();
        context = this.getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }

    public void addRecruitPost(Post newRecruitPost){
        recruitPosts.add(newRecruitPost);
    }
    public List<Post> getRecruitPosts(){
        return recruitPosts;
    }
    public void initRecruitPosts(List<Post> newRecruitPosts){ recruitPosts.addAll(newRecruitPosts);}

    public void addTreeholePost(Post newTreeholePost){
        treeholePosts.add(newTreeholePost);
    }
    public List<Post> getTreeholePosts(){
        return treeholePosts;
    }
    public void initTreeholePosts(List<Post> newTreeholePosts){ treeholePosts.addAll(newTreeholePosts);}

    public void addLostAndFoundPost(Post newLostAndFoundPost){ lostandfoundPosts.add(newLostAndFoundPost); }
    public List<Post> getLostAndFoundPosts(){
        return lostandfoundPosts;
    }
    public void initLostAndFoundPosts(List<Post> newLostAndFoundPosts){ lostandfoundPosts.addAll(newLostAndFoundPosts);}

    public void addCourseCommentPost(Post newCourseCommentPost){ courseCommentPosts.add(newCourseCommentPost); }
    public List<Post> getCourseCommentPosts(){
        return courseCommentPosts;
    }
    public void initCourseCommentPosts(List<Post> newCourseCommentPosts){ courseCommentPosts.addAll(newCourseCommentPosts);}

    public void addSharingPost(Post newSharingPost){ sharingPosts.add(newSharingPost); }
    public void initSharingPosts(List<Post> newSharingPosts){ sharingPosts.addAll(newSharingPosts);}
    public List<Post> getSharingPosts(){ return sharingPosts; }

    public void addExchangePost(Post newExchangePost){
        exchangePosts.add(newExchangePost);
    }
    public List<Post> getExchangePosts(){
        return exchangePosts;
    }
    public void initExchangePosts(List<Post> newExchangePosts){ exchangePosts.addAll(newExchangePosts);}

    public List<Post> getAllPosts(){
        return allPosts;
    }
    public void addAllPosts(Post post){
        allPosts.add(post);
    }
    public void initAllPosts(List<Post> newAllPosts){ allPosts.addAll(newAllPosts);}

    public List<Post> getMyPosts() { return myPosts; }
    public void addMyPost(Post post){
        myPosts.add(post);
    }
    public void initMyPosts(List<Post> newMyPosts){ myPosts.addAll(newMyPosts);}

    public List<Post> getMyLikePosts() {
        return myLikePosts;
    }
    public void addMyLikePost(Post post){
        myLikePosts.add(post);
    }
    public void initMyLikePosts(List<Post> newMyLikePosts){
        if(!myLikePosts.isEmpty())
            myLikePosts.clear();
        myLikePosts.addAll(newMyLikePosts);
    }

    public List<Post> getMyFavoritePosts() {
        return myFavoritePosts;
    }
    public void addMyFavoritePost(Post post){ myFavoritePosts.add(post); }
    public void initMyFavoritePosts(List<Post> newMyFavoritePosts){
        if(!myFavoritePosts.isEmpty())
            myFavoritePosts.clear();
        myFavoritePosts.addAll(newMyFavoritePosts);
    }

}
