package com.epsit.member;

import android.app.Application;

import com.epsit.arouter.ARouter;

public class MemberTestApplication extends Application {
    MemberTestApplication instance = null;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ARouter.getInstance().init(this);
    }
}
