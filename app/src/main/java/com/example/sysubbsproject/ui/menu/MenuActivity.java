package com.example.sysubbsproject.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sysubbsproject.MainActivity;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.ui.home.HomeActivity;
import com.example.sysubbsproject.ui.post.PostActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import cn.bmob.v3.BmobUser;

public class MenuActivity extends FragmentActivity {

    public static FragmentManager fm;
    private ChangeInformationFragment changeInformationFragment;
    private ShowLikesFragment showLikesFragment;
    private ShowFavoritesFragment showFavoritesFragment;
    private ShowPostsFragment showPostsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String viewType=bundle.getString("viewType");
        FragmentTransaction ft;

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        changeInformationFragment = new ChangeInformationFragment();
        showLikesFragment = new ShowLikesFragment();
        showFavoritesFragment = new ShowFavoritesFragment();
        showPostsFragment = new ShowPostsFragment();

        if(viewType.equals("information")){
            ft.add(R.id.container_my_menu, changeInformationFragment);
            ft.show(changeInformationFragment);
            ft.commit();
        }
        if(viewType.equals("post")){
            ft.add(R.id.container_my_menu, showPostsFragment);
            ft.show(showPostsFragment);
            ft.commit();
        }
        if(viewType.equals("likes")) {
            ft.add(R.id.container_my_menu, showLikesFragment);
            ft.show(showLikesFragment);
            ft.commit();
        }
        if(viewType.equals("favorites")) {
            ft.add(R.id.container_my_menu, showFavoritesFragment);
            ft.show(showFavoritesFragment);
            ft.commit();
        }
    }
}
