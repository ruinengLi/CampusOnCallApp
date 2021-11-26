package com.example.sysubbsproject.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.example.sysubbsproject.MainActivity;
import com.example.sysubbsproject.R;
import com.example.sysubbsproject.db.PersonHandler;
import com.example.sysubbsproject.db.bmob.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity{

    EditText loginUsername;
    EditText loginPassword;
    private Toast loginToast;
    private Toast successLogin;
    private Toast failedLogin;
    private Toast successSign;
    private Toast failedSign;
    private View fragmentSign,fragmentLogin;
    private CardView confirmLogin;

    protected int layoutID = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_welcome);
        fragmentLogin = (View)findViewById(R.id.fragment_login_2);
        fragmentSign = (View)findViewById(R.id.fragment_sign_2);
        fragmentSign.setVisibility(View.INVISIBLE);
        fragmentLogin.setVisibility(View.VISIBLE);
        confirmLogin = (CardView)findViewById(R.id.confirm_login);
        loginUsername = (EditText)findViewById(R.id.login_username);
        loginPassword = (EditText)findViewById(R.id.login_password);

        confirmLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String user_password = loginPassword.getText().toString().trim();
                String user_username = loginUsername.getText().toString().trim();

                if (user_username.isEmpty() || user_password.isEmpty()) {
                    loginToast = Toast.makeText(LoginActivity.this,"用户名或密码不能为空!", Toast.LENGTH_SHORT);
                    loginToast.show();
                }

                if(!checkLegalInput(user_username)){
                    loginToast = Toast.makeText(LoginActivity.this,"用户名不能含有非法字符!", Toast.LENGTH_SHORT);
                    loginToast.show();
                }

                PersonHandler.login(user_username, user_password, new SaveListener<Person>() {

                    @Override
                    public void done(Person res, BmobException e) {
                        if(e==null){
                            successLogin = Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT);
                            successLogin.show();
                            Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent_main);
                        }else{
                            if (e.getErrorCode() == 101) {
                                failedLogin = Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT);
                            }
                            else {
                                failedLogin = Toast.makeText(LoginActivity.this,"网络有问题，重试或可解决",Toast.LENGTH_LONG);
                            }
                            failedLogin.show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static boolean checkLegalInput(String target) {
        final String format = "[^\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w-_]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(target);
        return !matcher.find();
    }

}
