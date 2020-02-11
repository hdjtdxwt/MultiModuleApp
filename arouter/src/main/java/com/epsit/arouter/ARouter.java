package com.epsit.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class ARouter {
    static String TAG ="ARouter";
    private static final ARouter aRouter = new ARouter();//单例

    //这里仅仅转载activity的类对象
    private Map<String, Class<? extends Activity>> map;

    private Context context;

    private ARouter(){
        map = new HashMap<>();
    }

    public static ARouter getInstance(){
        return aRouter;
    }

    public void init(Context context ){
        this.context = context;
        //下面的方法是获取当前模块下这个包下的类列表
        //根据一个包名，获取所有这个包名下的类，这个包名是apt生成的,ActivityUtilxxxx.java，都在这个包下，名字加了时间戳
        List<Class> classNames = getClasssFromPackage("com.epsit.util");

        Log.e(TAG,"init classNames.size="+(classNames!=null ? classNames.size() : "-0"));
        for (Class aClass: classNames ) {
            try {
                /**
                 * isAssignableFrom()方法是判断是否为某个类的父类，instanceof关键字是判断是否某个类的子类
                 * 使用方法如下：
                 * 父类.class.isAssignableFrom(子类.class)
                 */

                if(IRouter.class.isAssignableFrom(aClass)){//aClass是否为IRouter.class的类型或延生类型，
                    IRouter iRouter = (IRouter) aClass.newInstance();

                    iRouter.putActivity();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void putActivity(String key, Class<? extends Activity> clazz){
        if(key!=null && clazz!=null){
            map.put(key, clazz);
        }

    }

    public Class getActivityByKey(String key){
        Log.e("getByKey","size="+map.size()+"  param:"+key);
        return map.get(key);
    }

    public void startActivity(String key){
        if(key!=null && !key.trim().equals("")){
            Class clazz = getActivityByKey(key);
            if(clazz!=null ){
                Intent intent = new Intent();
                intent.setClass(context ,clazz);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//默认是这个模式
                context.startActivity(intent);
            }else{
                Log.e("startActivity","clazz!=null ? "+(clazz!=null ));
                Log.e("startActivity","或者 目标不是activity子类："+ (clazz!=null ? clazz.getName():""));
            }
        }
    }
    public void startActivity(String key, int flags){
        if(key!=null && !key.trim().equals("")){
            Class clazz = getActivityByKey(key);
            if(clazz!=null ){
                Intent intent = new Intent();
                intent.setClass(context ,clazz);
                intent.addFlags(flags);
                context.startActivity(intent);
            }else{
                Log.e("startActivity","clazz!=null ? "+(clazz!=null ));
                Log.e("startActivity","或者 目标不是activity子类："+ (clazz!=null ? clazz.getName():""));
            }
        }
    }
    public void startActivity(String key, Bundle bundle){
        Class<? extends Activity> aClass = getActivityByKey(key);
        if(aClass!=null){
            Intent intent = new Intent(context, aClass);
            if(bundle!=null){
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }else{
            Log.e("startActivity",key+" 获取的aClass是空");
        }
    }

    public List<Class> getClasssFromPackage(String packageName){
        List<Class>classList = new ArrayList<>();
        String path = null;
        DexFile dexFile = null;
        try {
            path = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
            dexFile = new DexFile(path);
            Enumeration entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                String name = (String) entries.nextElement();
                if (name.startsWith(packageName)) {// 判断类的包名是否符合
                    classList.add(Class.forName(name));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dexFile != null) {
                    dexFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }
}
