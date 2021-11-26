package com.example.sysubbsproject.db;

import com.example.sysubbsproject.db.bmob.Post;

import java.util.List;

public class UpdatedPostHandler {

    public static List<Post> updatedPosts;

    /*
    public static void searchUpdatedPost(String viewType) {

        updatedPosts = new ArrayList<Post>();
        BmobQuery<Post> categoryBmobQuery = new BmobQuery<>();

        if(viewType.equals("treehole")){
            categoryBmobQuery.addWhereEqualTo("labels", "树洞");
        }
        if(viewType.equals("lostandfound")){
            categoryBmobQuery.addWhereEqualTo("labels", "失物招领");
        }
        if(viewType.equals("courseComment")){
            categoryBmobQuery.addWhereEqualTo("labels", "课程评价");
        }
        if(viewType.equals("exchange")){
            categoryBmobQuery.addWhereEqualTo("labels", "闲置");
        }
        if(viewType.equals("sharing")){
            categoryBmobQuery.addWhereEqualTo("labels", "资料分享");
        }
        if(viewType.equals("recruit")){
            categoryBmobQuery.addWhereEqualTo("labels", "招募");
        }

        categoryBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> object, BmobException e) {
                if (e == null) {
                    Toast.makeText(getContext(),String.valueOf(object.size()),Toast.LENGTH_LONG).show();
                    setUpdatedPosts(object);
                }
            }
        });
    }

    private static void setUpdatedPosts(List<Post> object){
        updatedPosts.clear();
        updatedPosts.addAll(object);
        Toast.makeText(getContext(),String.valueOf(updatedPosts.size()),Toast.LENGTH_LONG).show();
    }
    */



}
