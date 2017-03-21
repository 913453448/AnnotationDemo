package com.yasin.annotationdemo.annotation;

import android.view.View;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by leo on 17/3/21.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    int[]value();
    String listenerSetter() default "setOnClickListener";
    Class listenerType() default View.OnClickListener.class;
    String methodName() default "onClick";
}
