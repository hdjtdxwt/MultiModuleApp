package com.epsit.multimoduleapp;

import android.app.Application;

import com.epsit.arouter.ARouter;

public class ModuleApplication extends Application {
    private static ModuleApplication instance ;

    public static ModuleApplication getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ARouter.getInstance().init(this);
    }
}
