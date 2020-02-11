package com.epsit.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.epsit.annotation.BuildPath;
import com.epsit.arouter.ARouter;

//为了统一规则，BuildPath里的key是唯一的才行，同时最好要有个/,前面表示属于的module名，后面表示activity名字
@BuildPath("login/login")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler = new Handler(Looper.getMainLooper());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login_dump_member).setOnClickListener(this);
        findViewById(R.id.login_dump_main).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_dump_member) {//模拟登陆成功后去个人中心
            //ARouter.getInstance().startActivity("member/member_main", Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ARouter.getInstance().startActivity("member/member_main", Intent.FLAG_ACTIVITY_NEW_TASK);
            //startActivity(new Intent(this, LoginTestActivity.class));//同一个模块里的没有问题
            //startActivity(new Intent(this, MainActivity.class));//这个时候发现拿不到另一个module(也就是app里头的)的MainActivity.class，所以会想到组件通讯框架ARouter或者LiveDataBus或自己写一个组件通讯框架
        } else if(v.getId() == R.id.login_dump_main){
            //ARouter.getInstance().startActivity("main/main", Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ARouter.getInstance().startActivity("main/main", Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }
}
