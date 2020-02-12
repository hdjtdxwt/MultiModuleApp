package com.epsit.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.epsit.annotation.BuildPath;
import com.epsit.arouter.ARouter;

@BuildPath("member/member_main")
public class MemberMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为了防止资源名重复，这里修改类名和资源名称了
        setContentView(R.layout.activity_member_main);
        Log.e("MemberMain", "MemberMainActivity 的taskId:"+getTaskId() );

        findViewById(R.id.goLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.goLogin){
            //ARouter.getInstance().startActivity("login/login", Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //换成了new_TASK的模式,那么就会每次都创建一个,要么就给activity加上
            ARouter.getInstance().startActivity("login/login", Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }
}
