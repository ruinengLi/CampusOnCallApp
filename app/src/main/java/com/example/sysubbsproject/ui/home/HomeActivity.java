package com.example.sysubbsproject.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Post;

import java.util.List;

/* 首页界面管理类
* 包含首页布局容器，用于监听从首页界面点进板块时的按钮，从而调用相应界面
* */
public class HomeActivity extends FragmentActivity {

    public static FragmentManager fm;
    private TreeholeFragment treeholeFragment;
    private LostAndFoundFragment lostAndFoundFragment;
    private SharingFragment sharingFragment;
    private ExchangeFragment exchangeFragment;
    private CourseCommentFragment courseCommentFragment;
    private RecruitFragment recruitFragment;

    private PostItemAdapter postItemAdapter;
    private List<Post> postItemList = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        fm = getSupportFragmentManager();
        FragmentTransaction ft;

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String viewType=bundle.getString("viewType");

        ft = fm.beginTransaction();

        recruitFragment = new RecruitFragment();
        exchangeFragment = new ExchangeFragment();
        lostAndFoundFragment = new LostAndFoundFragment();
        courseCommentFragment = new CourseCommentFragment();
        sharingFragment = new SharingFragment();
        treeholeFragment = new TreeholeFragment();

        if(viewType.equals("recruit")){
            ft.add(R.id.container_home, recruitFragment);
            ft.show(recruitFragment);
            ft.commit();
        }
        if(viewType.equals("exchange")){
            ft.add(R.id.container_home, exchangeFragment);
            ft.show(exchangeFragment);
            ft.commit();
        }
        if(viewType.equals("sharing")){
            ft.add(R.id.container_home, sharingFragment);
            ft.show(sharingFragment);
            ft.commit();
        }
        if(viewType.equals("treehole")){
            ft.add(R.id.container_home, treeholeFragment);
            ft.show(treeholeFragment);
            ft.commit();
        }
        if(viewType.equals("lostandfound")){
            ft.add(R.id.container_home, lostAndFoundFragment);
            ft.show(lostAndFoundFragment);
            ft.commit();
        }
        if(viewType.equals("courseComment")){
            ft.add(R.id.container_home, courseCommentFragment);
            ft.show(courseCommentFragment);
            ft.commit();
        }
    }

}
