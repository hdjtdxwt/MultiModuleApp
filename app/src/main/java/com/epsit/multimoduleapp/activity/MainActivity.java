package com.epsit.multimoduleapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.epsit.annotation.BuildPath;
import com.epsit.multimoduleapp.R;

@BuildPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }
}
