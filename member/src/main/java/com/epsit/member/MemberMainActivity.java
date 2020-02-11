package com.epsit.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.epsit.annotation.BuildPath;

@BuildPath("member/member_main")
public class MemberMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为了防止资源名重复，这里修改类名和资源名称了
        setContentView(R.layout.activity_member_main);
    }
}
