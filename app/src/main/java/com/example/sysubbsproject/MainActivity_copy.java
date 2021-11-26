package com.example.sysubbsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;
import com.example.sysubbsproject.ui.login.LoginActivity;
import com.example.sysubbsproject.ui.login.SignInActivity;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ValueEventListener;


public class MainActivity_copy extends AppCompatActivity  {

    private Person person;
    private SpringSystem springSystem = SpringSystem.create();
    private Spring rotate;

    public static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bmob.initialize(this, "0bfb286060286e0ea823f12bfb67a58e");
        /* 这里设置一下超时时间，默认15s太长了 */
        BmobConfig config = new BmobConfig.Builder(this).setApplicationId("dd72b7a3ae0856c104a6a3cf4526e203").setConnectTimeout(5).build();
        Bmob.initialize(config);

        rotate = springSystem.createSpring();
        rotate.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(15, 8));


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

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity_copy.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity_copy.this, SignInActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    Post post = new Post();
                    post.setAuthorName(data.optString("authorName"));
                    post.setObjectId(data.optString("objectId"));
                    post.setContent(data.optString("PostText"));
                    post.setTitle(data.optString("PostTitle"));
                    post.setNumFavorites(data.optInt("favorites"));
                    post.setNumLikes(data.optInt("likes"));
                    post.setNumComments(data.optInt("comment"));

                    app.addAllPosts(post);
                    if(data.optString("labels").equals("树洞")){
                        app.addTreeholePost(post);
                    }
                    else if(data.optString("labels").equals("招募")){
                        app.addRecruitPost(post);
                    }
                    if(person.getObjectId().equals(BmobUser.getCurrentUser(Person.class).getObjectId())){
                        app.addMyPost(post);
                    }
                }
            }
        });
    }

}
