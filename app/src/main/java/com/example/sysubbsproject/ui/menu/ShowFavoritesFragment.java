package com.example.sysubbsproject.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sysubbsproject.Application;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.ui.home.PostItemAdapter;

public class ShowFavoritesFragment extends Fragment {

    private PostItemAdapter postItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_favorites,container,false);

        Application app = (Application)Application.getContext();
        postItemAdapter = new PostItemAdapter(getContext(),app.getMyFavoritePosts());
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_my_favorites);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(postItemAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;

    }
}
