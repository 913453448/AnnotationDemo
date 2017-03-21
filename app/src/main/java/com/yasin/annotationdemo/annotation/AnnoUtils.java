package com.yasin.annotationdemo.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by leo on 17/3/21.
 */

public class AnnoUtils {

    private static final String TAG = "AnnoUtils";

    public static void inject(Activity activity) {
        if (activity == null) return;
        try {
            //获取当前activity的镜像
            Class<?> clazz = activity.getClass();
            //获取当前activity中所有的字段
            Field[] fields = clazz.getDeclaredFields();
            //开始遍历所有的字段
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    //判断当前字段是否支持该注解
                    if (field.isAnnotationPresent(ViewInject.class)) {
                        //获取ViewInject注解对象
                        ViewInject annotation = field.getAnnotation(ViewInject.class);
                        if (annotation != null) {
                            int id = annotation.value();
                            View view = activity.findViewById(id);
                            if (view != null) {
                                //把view赋给字段filed
                                field.setAccessible(true);
                                field.set(activity, view);
                            }
                        }
                    }
                }
            }

            //获取activity中所有的方法
            Method[] methods=clazz.getDeclaredMethods();
            if(methods!=null&&methods.length>0){
                //遍历所有的方法
                for (Method method:methods) {
                    //找到带有OnClick注解标记的方法
                    if(method.isAnnotationPresent(OnClick.class)){
                        OnClick annotation=method.getAnnotation(OnClick.class);
                        //获取所有的id
                        int[] ids = annotation.value();
                        //遍历所有的id
                        for (int id: ids) {
                            //找到相应的view
                            View view=activity.findViewById(id);
                            if(view!=null){
                                method.setAccessible(true);
                                //获取onclicklistener镜像
                                Class<?> listenerType = annotation.listenerType();
                                //获取view中的setOnClickListener方法名字
                                String listenerSetter = annotation.listenerSetter();
                                //系统调用onClick方法名
                                String methodName = annotation.methodName();
                                //创建代理对象帮助类
                                ViewHandler handler=new ViewHandler(activity,method,methodName);
                                //获取onClickListener代理对象
                                Object onClickListener=Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
                                //获取view的setOnClickListener方法
                                Method setOnListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                                //实现setOnClickListener方法
                                setOnListenerMethod.invoke(view,onClickListener);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }
}
