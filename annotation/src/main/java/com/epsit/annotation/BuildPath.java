package com.epsit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//所有的注解都可以放到这个java的library中，但是注解处理器建议单独分开到各自的library里
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BuildPath {

    String value();

}
