package com.yasin.annotationdemo.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by leo on 17/3/21.
 */

public class ViewHandler implements InvocationHandler {
    private Object object;
    private Method mMethod;
    private String methodName;
    public ViewHandler(Object object,Method method,String methodName){
        this.object=object;
        this.mMethod=method;
        this.methodName=methodName;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //当系统调用onclick方法的时候，我们就调用我们在activity中定义的方法
        if(method.getName().equals(methodName)){
            Object obj = mMethod.invoke(object, args);
            return obj;
        }
        return null;
    }
}
