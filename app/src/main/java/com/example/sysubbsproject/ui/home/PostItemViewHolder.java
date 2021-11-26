package com.example.sysubbsproject.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;

class PostItemViewHolder extends RecyclerView.ViewHolder {

    ImageView post_profile,post_favorite,post_favorite_2,post_comment,post_comment_2,post_likes,post_likes_2;
    TextView post_title,post_username,post_time,post_content,post_favorite_num,post_comment_num,post_likes_num;
    int favorite_click_num = 0;
    int likes_click_num = 0;

    Person person;

    public PostItemViewHolder(@NonNull View itemView) {
        super(itemView);

        post_profile = (ImageView)itemView.findViewById(R.id.post_profile);
        post_username = (TextView)itemView.findViewById(R.id.post_username);
        post_time = (TextView)itemView.findViewById(R.id.post_time);
        post_content = (TextView)itemView.findViewById(R.id.post_content);
        post_title = (TextView)itemView.findViewById(R.id.post_title);

        post_favorite = (ImageView)itemView.findViewById(R.id.post_favorite);
        post_favorite_2 = (ImageView)itemView.findViewById(R.id.post_favorite_2);
        post_favorite_num = (TextView)itemView.findViewById(R.id.post_favorite_num);

        post_comment = (ImageView)itemView.findViewById(R.id.post_comment);
        post_comment_2 = (ImageView)itemView.findViewById(R.id.post_comment_2);
        post_comment_num = (TextView)itemView.findViewById(R.id.post_comment_num);

        post_likes = (ImageView)itemView.findViewById(R.id.post_likes);
        post_likes_2 = (ImageView)itemView.findViewById(R.id.post_likes_2);
        post_likes_num = (TextView)itemView.findViewById(R.id.post_likes_num);

    }

}
