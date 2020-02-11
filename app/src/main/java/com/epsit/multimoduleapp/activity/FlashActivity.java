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
                //startActivity(new Intent(ModuleApplication.getInstance(), MainActivity.class));
                ARouter.getInstance().startActivity("main/main");
            }
        },2000);
    }
}
