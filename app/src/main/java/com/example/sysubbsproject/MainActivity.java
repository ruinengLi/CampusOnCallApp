package com.example.sysubbsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sysubbsproject.db.PostHandler;
import com.example.sysubbsproject.db.bmob.Post;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.sysubbsproject.ui.login.LoginActivity;
import com.example.sysubbsproject.ui.login.SignInActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ValueEventListener;

import com.example.sysubbsproject.db.bmob.Person;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.sysubbsproject.Application.getContext;


public class MainActivity extends AppCompatActivity  {

    private Person person;
    private SpringSystem springSystem = SpringSystem.create();
    private Spring rotate;

    public static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bmob.initialize(this, "0bfb286060286e0ea823f12bfb67a58e")
        // 设置超时时间
        BmobConfig config = new BmobConfig.Builder(this).setApplicationId("dd72b7a3ae0856c104a6a3cf4526e203").setConnectTimeout(5).build();
        Bmob.initialize(config);

        // 登录注册界面旋转效果
        rotate = springSystem.createSpring();
        rotate.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(15, 8));

        // 判断是否登录，未登录跳转至登陆/注册界面，否则直接进入首页
        if (BmobUser.isLogin()) {
            setContentView(R.layout.activity_main);

            person = BmobUser.getCurrentUser(Person.class);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_post, R.id.navigation_messages, R.id.navigation_my_menu)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.setCustomView(R.layout.fragment_title);
                TextView textView = (TextView) actionBar.getCustomView().findViewById(R.id.display_title);
                textView.setText("达可达可");
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowCustomEnabled(true);
            }
            // 导航栏监听
            navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if(item.getItemId()==R.id.navigation_home_selected) {
                        if (item.isChecked()) {
                            return true;
                        }
                        item.setChecked(true);
                        navController.navigate(R.id.navigation_home);
                    }
                    if(item.getItemId()==R.id.navigation_post_selected){
                        if(item.isChecked()){
                            return true;
                        }
                        item.setChecked(true);
                        navController.navigate(R.id.navigation_post);
                    }
                    if(item.getItemId()==R.id.navigation_my_menu_selected){
                        if(item.isChecked()){
                            return true;
                        }
                        item.setChecked(true);
                        navController.navigate(R.id.navigation_my_menu);
                    }
                    if(item.getItemId()==R.id.navigation_messages_selected){
                        if(item.isChecked()){
                            return true;
                        }
                        item.setChecked(true);
                        navController.navigate(R.id.navigation_messages);
                    }
                    return false;
                }
            });
            // 初始化各类帖子全局变量
            initPosts();
            // 异步监听帖子更新情况
            startBmobRealTimeData();

        }
        else {
            setContentView(R.layout.activity_login);
            TextView has_no_account,go_to_login;
            CardView login,sign;
            View fragmentLogin,fragmentSign;
            has_no_account = (TextView)findViewById(R.id.has_no_account);
            go_to_login = (TextView)findViewById(R.id.go_to_login);
            fragmentLogin = (View)findViewById(R.id.fragment_login);
            fragmentSign = (View)findViewById(R.id.fragment_sign);
            login = (CardView)findViewById(R.id.login);
            sign = (CardView)findViewById(R.id.sign);

            // 监听登录/注册页面切换按钮
            has_no_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentLogin.setVisibility(View.INVISIBLE);
                    fragmentSign.setVisibility(View.VISIBLE);

                    rotate.setCurrentValue(0);
                    fragmentSign.setCameraDistance(80000.0f);
                    rotate.addListener(new SimpleSpringListener() {
                        @Override
                        public void onSpringUpdate(Spring spring) {
                            fragmentSign.setRotationY((float) spring.getCurrentValue());
                        }
                        @Override
                        public void onSpringAtRest(Spring spring) {

                        }
                    });
                    rotate.setEndValue(360.0f);
                }
            });

            // 监听注册/登录页面切换按钮
            go_to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentSign.setVisibility(View.INVISIBLE);
                    fragmentLogin.setVisibility(View.VISIBLE);
                    rotate.setCurrentValue(0);
                    fragmentLogin.setCameraDistance(80000.0f);
                    rotate.addListener(new SimpleSpringListener() {
                        @Override
                        public void onSpringUpdate(Spring spring) {
                            fragmentLogin.setRotationY((float) spring.getCurrentValue());
                        }

                        @Override
                        public void onSpringAtRest(Spring spring) {

                        }
                    });
                    rotate.setEndValue(360.0f);
                }
            });

            // 监听登录按钮
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

            // 监听注册按钮
            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initPosts(){

        List<Post> initTreeholePosts,initExchangePosts,initCourseCommentPosts,initSharingPosts,initRecruitPosts,initLostAndFoundPosts;

        // 获取已经发布的树洞板块帖子
        PostHandler.GetPosts("树洞",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                if(!app.getTreeholePosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initTreeholePosts(clear_posts);
                }
                Collections.reverse(post_lst);
                app.initTreeholePosts(post_lst);
            }
        });

        // 获取已经发布的闲置板块帖子
        PostHandler.GetPosts("闲置",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                if(!app.getExchangePosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initExchangePosts(clear_posts);
                }
                Collections.reverse(post_lst);
                app.initExchangePosts(post_lst);
            }
        });

        // 获取已经发布的失物招领板块帖子
        PostHandler.GetPosts("失物招领",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                if(app.getLostAndFoundPosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initLostAndFoundPosts(clear_posts);
                }
                Collections.reverse(post_lst);
                app.initLostAndFoundPosts(post_lst);
            }
        });

        // 获取已经发布的招募板块帖子
        PostHandler.GetPosts("招募",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                if(app.getRecruitPosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initRecruitPosts(clear_posts);
                }
                Collections.reverse(post_lst);
                app.initRecruitPosts(post_lst);
            }
        });

        // 获取已经发布的资料分享板块帖子
        PostHandler.GetPosts("资料分享",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                if(app.getSharingPosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initSharingPosts(clear_posts);
                }
                Collections.reverse(post_lst);
                app.initSharingPosts(post_lst);
            }
        });

        // 获取已经发布的课程评价板块帖子
        PostHandler.GetPosts("课程评价",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                if(app.getCourseCommentPosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initCourseCommentPosts(clear_posts);
                }
                Collections.reverse(post_lst);
                app.initCourseCommentPosts(post_lst);
            }
        });

        // 获取全部帖子
        PostHandler.GetPosts("",100,0, new FindListener<Post>() {
            @Override
            public void done(List<Post> post_lst, BmobException e) {
                Application app = (Application)getApplication();
                List<Post> tmp_myPosts = new ArrayList<Post>();
                if(app.getAllPosts().isEmpty()){
                    List<Post> clear_posts = new ArrayList<Post>();
                    app.initAllPosts(clear_posts);
                }
                for(Post post:post_lst) {
                    String authorObjectId = post.getAuthor().getObjectId();
                    if (authorObjectId.equals(BmobUser.getCurrentUser(Person.class).getObjectId())) {
                        tmp_myPosts.add(post);
                    }
                }
                Collections.reverse(post_lst);
                app.initAllPosts(post_lst);
                Collections.reverse(tmp_myPosts);
                app.initMyPosts(tmp_myPosts);
                Toast.makeText(getContext(),"初始化完成",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void startBmobRealTimeData(){

        final BmobRealTimeData bmobRealTimeData = new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if(e == null){
                    if(bmobRealTimeData.isConnected()){
                        bmobRealTimeData.subTableUpdate("Post");
                     }
                }
            }
            @Override
            public void onDataChange(JSONObject jsonObject) {
                Gson gson = new Gson();
                String action = jsonObject.optString("action");
                String jsonString = gson.toJson(jsonObject);

                if(action.equals(BmobRealTimeData.ACTION_UPDATETABLE)){
                    JSONObject data = jsonObject.optJSONObject("data");
                    Application app =  (Application)getApplication();

                    String postId = data.optString("objectId");
                    BmobQuery<Post> post_BmobQuery = new BmobQuery<>();
                    post_BmobQuery.addWhereEqualTo("objectId",postId);
                    post_BmobQuery.findObjects(new FindListener<Post>() {
                        @Override
                        public void done(List<Post> post_lst, BmobException e) {

                            Post post = post_lst.get(0);
                            String authorId = data.optString("userPtr");
                            BmobQuery<Person> authorBmobQuery = new BmobQuery<>();
                            authorBmobQuery.addWhereEqualTo("objectId", authorId);
                            authorBmobQuery.findObjects(new FindListener<Person>() {
                                @Override
                                public void done(List<Person> object, BmobException e) {
                                    if (e == null) {
                                        post.setAuthorName(object.get(0).getUsername());
                                        post.setAuthorProfile(object.get(0).getProfile());
                                        app.addAllPosts(post);
                                        if(data.optString("labels").equals("树洞")){
                                            app.addTreeholePost(post);
                                        }
                                        else if(data.optString("labels").equals("招募")){
                                            app.addRecruitPost(post);
                                        }
                                        else if(data.optString("labels").equals("失物招领")){
                                            app.addLostAndFoundPost(post);
                                        }
                                        else if(data.optString("labels").equals("闲置")){
                                            app.addExchangePost(post);
                                        }
                                        else if(data.optString("labels").equals("资料分享")){
                                            app.addSharingPost(post);
                                        }
                                        else if(data.optString("labels").equals("课程评价")){
                                            app.addCourseCommentPost(post);
                                        }

                                        if(authorId.equals(BmobUser.getCurrentUser(Person.class).getObjectId())){
                                            app.addMyPost(post);
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

}
