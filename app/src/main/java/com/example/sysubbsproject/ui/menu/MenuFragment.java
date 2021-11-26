package com.example.sysubbsproject.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sysubbsproject.Application;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;
import com.example.sysubbsproject.db.bmob.Post;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MenuFragment extends Fragment {

    ImageView profile;
    TextView phoneNumber;
    LinearLayout information,likes,post,favorites;
    Person person;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_menu,container,false);

        profile = v.findViewById(R.id.profile_my_menu);
        phoneNumber = v.findViewById(R.id.tel_my_menu);
        information = v.findViewById(R.id.information_my_menu);
        likes = v.findViewById(R.id.likes_my_menu);
        post = v.findViewById(R.id.post_my_menu);
        favorites = v.findViewById(R.id.favorites_my_menu);

        person = BmobUser.getCurrentUser(Person.class);
        if(person.getProfile()!=null){
            Glide.with(getContext()).load(person.getProfile()).into(profile);
        }
        phoneNumber.setText(person.getMobilePhoneNumber());

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MenuActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "information";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobQuery<Post> likesQuery = new BmobQuery<Post>();
                likesQuery.addWhereRelatedTo("myLikes_App",new BmobPointer(BmobUser.getCurrentUser(Person.class)));
                likesQuery.findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> post_lst, BmobException e) {

                        if(e == null){
                            Application app = (Application)Application.getContext();
                            app.initMyFavoritePosts(post_lst);
                        }
                        else{
                            Toast.makeText(getContext(),"你还没有点赞过帖子!",Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(getContext(),MenuActivity.class);
                        Bundle bundle=new Bundle();
                        String viewType = "favorites";
                        bundle.putString("viewType",viewType);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Post> favoritesQuery = new BmobQuery<Post>();
                Person person = new Person();
                person.setObjectId(BmobUser.getCurrentUser(Person.class).getObjectId());
                favoritesQuery.addWhereRelatedTo("myCollections_App", new BmobPointer(person));
                favoritesQuery.findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> post_lst, BmobException e) {
                        if(e == null){
                            System.out.println(post_lst.size());
                            Application app = (Application)Application.getContext();
                            app.initMyFavoritePosts(post_lst);
                            Toast.makeText(getContext(),"finish loading my favorites!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(),"你还没有收藏过帖子!",Toast.LENGTH_LONG).show();
                            System.out.println(e.getMessage());
                        }
                        Intent intent = new Intent(getContext(),MenuActivity.class);
                        Bundle bundle=new Bundle();
                        String viewType = "favorites";
                        bundle.putString("viewType",viewType);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MenuActivity.class);
                Bundle bundle=new Bundle();
                String viewType = "post";
                bundle.putString("viewType",viewType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return v;
    }
}
