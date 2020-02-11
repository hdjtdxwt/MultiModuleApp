package com.epsit.multimoduleapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.epsit.annotation.BuildPath;
import com.epsit.arouter.ARouter;
import com.epsit.multimoduleapp.ModuleApplication;
import com.epsit.multimoduleapp.R;

@BuildPath("main/flash")
public class FlashActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(ModuleApplication.getInstance(), MainActivity.class));//拿不到外部module的MainActivity或者其他的activity的class
                //ARouter.getInstance().startActivity("main/main", Intent.FLAG_ACTIVITY_SINGLE_TOP);//这是跳转自己module里头的activity所以用singleTask或singleTop
                ARouter.getInstance().startActivity("main/main", Intent.FLAG_ACTIVITY_NEW_TASK);
                //按理同一个module里的activity，应该可以用singleTop，结果还是报错如下了，所以项目还是有问题待优化
                //android.util.AndroidRuntimeException: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?

                //启动外部module的activity需要设置NEW_TASK的模式不然会报错的，所以尽量用默认的没有FLAG的哪个方法
                //还没有试启动service的情况
            }
        },2000);
    }
}
