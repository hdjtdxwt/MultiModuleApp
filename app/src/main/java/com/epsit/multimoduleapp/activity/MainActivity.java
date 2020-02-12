package com.epsit.multimoduleapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.epsit.annotation.BuildPath;
import com.epsit.arouter.ARouter;
import com.epsit.multimoduleapp.R;

@BuildPath("main/main")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity","MainActivity的taskId:"+ getTaskId() );
        findViewById(R.id.goMember).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goMember:{
                Log.e(TAG,"goMember-->onClick==>去子模块的个人中心");

                //ARouter.getInstance().startActivity("member/member_main", Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //启动自己module的可以用singleTop模式，但是module外的好像不行，会报错，NEW_TASK模式才可以
                ARouter.getInstance().startActivity("member/member_main", Intent.FLAG_ACTIVITY_NEW_TASK);
            }break;
        }
    }
}
