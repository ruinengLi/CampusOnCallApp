package com.example.sysubbsproject.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;

import cn.bmob.v3.BmobUser;

public class ShowInformationFragment extends Fragment {

    Person person;
    TextView nickname,gender,country,birthday,signature,city;
    CardView change,back;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_information, container, false);

        person = BmobUser.getCurrentUser(Person.class);

        /*
        nickname = v.findViewById(R.id.information_1_nick);
        gender = v.findViewById(R.id.information_1_gender);
        country = v.findViewById(R.id.information_1_country);
        birthday = v.findViewById(R.id.information_1_birthday);
        signature = v.findViewById(R.id.information_1_signature);
        city = v.findViewById(R.id.information_1_city);

        change = v.findViewById(R.id.information_1_change);
        back = v.findViewById(R.id.information_1_back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.changeFragment(new MenuFragment(),R.id.fragment_my_menu);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.showFragment()
            }
        });

        */
        return v;
    }

}
