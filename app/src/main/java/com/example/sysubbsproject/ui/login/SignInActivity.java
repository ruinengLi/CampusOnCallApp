package com.example.sysubbsproject.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.PersonHandler;
import com.example.sysubbsproject.db.bmob.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignInActivity extends AppCompatActivity {

    Person person = new Person();

    EditText signUsername;
    EditText signPassword;
    private Toast signToast;
    private Toast successSign;
    private Toast failedSign;
    private View fragmentSign,fragmentLogin;
    private CardView confirmSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_welcome);
        fragmentLogin = (View)findViewById(R.id.fragment_login_2);
        fragmentSign = (View)findViewById(R.id.fragment_sign_2);
        fragmentSign.setVisibility(View.VISIBLE);
        fragmentLogin.setVisibility(View.INVISIBLE);
        confirmSign = (CardView)findViewById(R.id.confirm_sign);
        signUsername = (EditText)findViewById(R.id.regist_username);
        signPassword = (EditText)findViewById(R.id.regist_password);

        confirmSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_username = signUsername.getText().toString();
                String user_password = signPassword.getText().toString().trim();

                if (user_username.isEmpty() || user_password.isEmpty()) {
                    signToast = Toast.makeText(SignInActivity.this,"用户名或密码不能为空!", Toast.LENGTH_SHORT);
                    signToast.show();
                }

                if(!checkLegalInput(user_username)){
                    signToast = Toast.makeText(SignInActivity.this,"用户名不能含有非法字符!", Toast.LENGTH_SHORT);
                    signToast.show();
                }

                // 使用BmobSDK提供的注册功能
                person.setUsername(user_username);
                person.setPassword(user_password);

                PersonHandler.registerNewPerson(user_username, user_password, new SaveListener<Person>() {
                            @Override
                            public void done(Person res, BmobException e) {
                                if(e==null){
                                    Toast.makeText(SignInActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    //loge(e);
                                    Toast.makeText(SignInActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
            }
        });
    }

    public static boolean checkLegalInput(String target) {
        final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w-_]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(target);
        return !matcher.find();
    }
}
