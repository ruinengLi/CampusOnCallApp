package com.example.sysubbsproject.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sysubbsproject.Application;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Post;
import com.example.sysubbsproject.ui.home.PostItemAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowPostsFragment extends Fragment {

    private PostItemAdapter postItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_posts,container,false);

        Application app = (Application)Application.getContext();
        List<Post> tmp_posts = new ArrayList<Post>(app.getMyPosts());
        Collections.reverse(tmp_posts);
        postItemAdapter = new PostItemAdapter(getContext(),tmp_posts);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_my_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(postItemAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;

    }
}
