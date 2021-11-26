package com.example.sysubbsproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sysubbsproject.Application;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    LinearLayout treehole,courseComment,sharing,exchange,lostandfound,recruit;
    private PostItemAdapter postItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        treehole = root.findViewById(R.id.home_treehole);
        courseComment = root.findViewById(R.id.home_courseComment);
        sharing = root.findViewById(R.id.home_sharing);
        lostandfound = root.findViewById(R.id.home_lostandfound);
        exchange = root.findViewById(R.id.home_exchange);
        recruit = root.findViewById(R.id.home_recruit);

        Application app = (Application)Application.getContext();
        List<Post> tmp_posts = new ArrayList<Post>();
        tmp_posts.addAll(app.getAllPosts());
        Collections.reverse(tmp_posts);
        postItemAdapter = new PostItemAdapter(getContext(),tmp_posts);

        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycler_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postItemAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 监听树洞按钮，跳转到HomeActivity
        treehole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "treehole";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 监听课程评价按钮，跳转到HomeActivity
        courseComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "courseComment";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 监听资料分享按钮，跳转到HomeActivity
        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "sharing";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 监听失物招领按钮，跳转到HomeActivity
        lostandfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "lostandfound";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 监听招募按钮，跳转到HomeActivity
        recruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "recruit";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 监听闲置按钮，跳转到HomeActivity
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "exchange";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return root;
    }
}