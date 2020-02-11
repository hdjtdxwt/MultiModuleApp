package com.epsit.arouter;

//后面还有StartService没有完善，所以就暂时这么两个
public interface IRouter {
    void putActivity();

    //这里没有对putActivity里的参数做限制
    Class getClassByKey(String key);
}
