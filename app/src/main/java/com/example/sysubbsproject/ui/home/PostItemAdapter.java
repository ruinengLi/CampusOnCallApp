package com.example.sysubbsproject.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PostItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull

    View postItemView;
    private Context context;
    private List<Post> postItemList;
    private LayoutInflater layoutInflater;

    public PostItemAdapter(Context context, List<Post> postItemArrayList) {

        this.context = context;
        this.postItemList = postItemArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        postItemView = layoutInflater.inflate(R.layout.home_item,parent,false);
        return new PostItemViewHolder(postItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder.getClass().equals(PostItemViewHolder.class)) {

            Post postItem = this.postItemList.get(position);
            String profile = postItem.getAuthorProfile();
            String username = postItem.getAuthorName();
            System.out.println(username);
            String publishTime = postItem.getCreatedAt();
            String content = postItem.getContent();
            String title = postItem.getTitle();

            int numComments = postItem.getNumComments();
            int numLikes = postItem.getNumLikes();
            int numFavorites = postItem.getNumFavorites();

            if(profile!=null) {
                Glide.with(this.context).load(profile).into(((PostItemViewHolder) holder).post_profile);
            }
            else{
                ((PostItemViewHolder) holder).post_profile.setBackgroundResource(R.drawable.information_my_menu);
            }

            ((PostItemViewHolder)holder).post_title.setText(title);
            ((PostItemViewHolder) holder).post_username.setText(username);
            ((PostItemViewHolder) holder).post_time.setText(publishTime);
            ((PostItemViewHolder) holder).post_content.setText(content);
            ((PostItemViewHolder) holder).post_comment_num.setText(String.valueOf(numComments));
            ((PostItemViewHolder) holder).post_likes_num.setText(String.valueOf(numLikes));
            ((PostItemViewHolder) holder).post_favorite_num.setText(String.valueOf(numFavorites));

            // 监听收藏/取消收藏
            ((PostItemViewHolder)holder).post_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((PostItemViewHolder)holder).post_favorite.getVisibility()==View.VISIBLE){
                        ((PostItemViewHolder)holder).post_favorite.setVisibility(View.INVISIBLE);
                        ((PostItemViewHolder)holder).post_favorite_2.setVisibility(View.VISIBLE);
                        postItem.setNumFavorites(false);
                        int _numFavorites = postItem.getNumFavorites();
                        ((PostItemViewHolder) holder).post_favorite_num.setText(String.valueOf(_numFavorites));

                        postItem.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                BmobRelation bmobRelation = new BmobRelation();
                                Person person = BmobUser.getCurrentUser(Person.class);
                                bmobRelation.remove(postItem);
                                person.setMyCollections(bmobRelation);
                                person.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                    }
                                });
                            }
                        });
                    }
                }
            });

            ((PostItemViewHolder)holder).post_favorite_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((PostItemViewHolder)holder).post_favorite_2.getVisibility()==View.VISIBLE){
                        ((PostItemViewHolder)holder).post_favorite.setVisibility(View.VISIBLE);
                        ((PostItemViewHolder)holder).post_favorite_2.setVisibility(View.INVISIBLE);
                        postItem.setNumFavorites(true);
                        int _numFavorites = postItem.getNumFavorites();
                        ((PostItemViewHolder) holder).post_favorite_num.setText(String.valueOf(_numFavorites));
                        postItem.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                BmobRelation bmobRelation = new BmobRelation();
                                Person person = BmobUser.getCurrentUser(Person.class);
                                bmobRelation.add(postItem);
                                person.setMyCollections(bmobRelation);
                                person.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                    }
                                });
                            }
                        });
                    }
                }
            });

            // 监听点赞/取消点赞
            ((PostItemViewHolder)holder).post_likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((PostItemViewHolder)holder).post_likes.getVisibility() == View.VISIBLE){
                        ((PostItemViewHolder)holder).post_likes.setVisibility(View.INVISIBLE);
                        ((PostItemViewHolder)holder).post_likes_2.setVisibility(View.VISIBLE);
                        postItem.setNumLikes(false);
                        int _numLikes = postItem.getNumLikes();
                        ((PostItemViewHolder) holder).post_likes_num.setText(String.valueOf(_numLikes));

                        postItem.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                BmobRelation bmobRelation = new BmobRelation();
                                Person person = BmobUser.getCurrentUser(Person.class);
                                bmobRelation.remove(postItem);
                                person.setMyLikes(bmobRelation);
                                person.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                    }
                                });
                            }
                        });
                    }
                }
            });

            ((PostItemViewHolder)holder).post_likes_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(((PostItemViewHolder)holder).post_likes_2.getVisibility()==View.VISIBLE){
                        ((PostItemViewHolder)holder).post_likes.setVisibility(View.VISIBLE);
                        ((PostItemViewHolder)holder).post_likes_2.setVisibility(View.INVISIBLE);
                        postItem.setNumLikes(true);
                        int _numLikes = postItem.getNumLikes();
                        ((PostItemViewHolder) holder).post_likes_num.setText(String.valueOf(_numLikes));

                        postItem.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                BmobRelation bmobRelation = new BmobRelation();
                                Person person = BmobUser.getCurrentUser(Person.class);
                                bmobRelation.add(postItem);
                                person.setMyLikes(bmobRelation);
                                person.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                    }
                                });
                            }
                        });
                    }
                }
            });
            ((PostItemViewHolder) holder).post_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 集成后，点头像进个人主页
                }
            });

            ((PostItemViewHolder) holder).post_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 集成后，点名字进个人主页
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }
}
