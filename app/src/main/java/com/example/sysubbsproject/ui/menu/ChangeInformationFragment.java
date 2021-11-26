package com.example.sysubbsproject.ui.menu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.bmob.Person;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeInformationFragment extends Fragment {

    TextInputEditText nickname,city,country;
    TextView birthday,gender;
    LinearLayout birthday_layout,gender_layout;
    Person person;
    ImageView profile;
    MaterialAlertDialogBuilder genderDialog;
    MaterialDatePicker<Long> birthdayPicker;
    String[] genderChoice = {"男", "女"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_information,container,false);
        nickname = v.findViewById(R.id.information_nick);
        city = v.findViewById(R.id.information_city);
        country = v.findViewById(R.id.information_country);
        birthday = v.findViewById(R.id.information_birthday);
        gender = v.findViewById(R.id.information_gender);
        profile = v.findViewById(R.id.information_profile);

        person = BmobUser.getCurrentUser(Person.class);

        if(person.getNick()!= null){
            nickname.setText(person.getNick());
        }
        if(person.getGender()!= null){
            gender.setText(person.getGender());
        }
        if(person.getCountry()!= null){
            country.setText(person.getCountry());
        }
        if(person.getBirthday()!= null){
            birthday.setText(person.getBirthday());
        }
        if(person.getCity()!= null){
            city.setText(person.getCity());
        }
        if(person.getProfile()!=null){
            Glide.with(getContext()).load(person.getProfile()).into(profile);
        }

        nickname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input_nickname = nickname.getText().toString().trim();
                if(!input_nickname.equals("")){
                    person.setNick(input_nickname);
                    nickname.setText(input_nickname);
                    person.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getContext(), "更新用户昵称成功为" + person.getNick(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "更新用户所在城市失败,请重试" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                return false;
            }
        });

        city.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input_city = city.getText().toString().trim();
                if(!input_city.equals("")){
                    person.setCity(input_city);
                    city.setText(input_city);
                    person.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getContext(), "更新用户所在城市成功为" + person.getCity(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "更新用户所在城市失败,请重试" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                return false;
            }
        });

        country.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String input_country = country.getText().toString().trim();
                if(!input_country.equals("")){
                    person.setCountry(input_country);
                    country.setText(input_country);

                    person.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getContext(), "更新用户所在国家成功为" + person.getCountry(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "更新用户所在国家失败,请重试" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                return false;
            }
        });

        birthday_layout = v.findViewById(R.id.information_layout_birthday);
        gender_layout = v.findViewById(R.id.information_layout_gender);

        genderDialog = new MaterialAlertDialogBuilder(getContext(),R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
                .setTitle("性别")
                .setSingleChoiceItems(genderChoice,0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), genderChoice[which], Toast.LENGTH_SHORT).show();
                        person.setGender(genderChoice[which]);
                        gender.setText(genderChoice[which]);
                        dialog.dismiss();
                        person.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getContext(), "更新用户性别成功为" + person.getGender(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "更新用户性别失败,请重试" , Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

        birthdayPicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("生日日期")
                .setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
                .setSelection((MaterialDatePicker.todayInUtcMilliseconds()))
                .build();

        birthdayPicker.addOnPositiveButtonClickListener(selection ->{

            Long input_birthday = birthdayPicker.getSelection();
            Date date = new Date(input_birthday);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String user_birthday = sdf.format(date);
            person.setBirthday(user_birthday);
            birthday.setText(user_birthday);

            person.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(getContext(), "更新用户生日成功为" + person.getBirthday(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "更新用户生日失败,请重试" , Toast.LENGTH_LONG).show();
                    }
                }
            });
        });

        birthdayPicker.addOnNegativeButtonClickListener(selection -> {
            birthdayPicker.onStop();
        });


        birthday_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdayPicker.show(getActivity().getSupportFragmentManager(),"birthdayPickup");
            }
        });

        gender_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = genderDialog.create();
                dialog.show();
            }
        });

        return v;
    }

}
