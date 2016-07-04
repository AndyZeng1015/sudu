package com.zyn.sudu.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import com.zyn.sudu.base.BaseApplication;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/14
 * Time:17:13
 * Desc: 和UI相关的工具类
 */
public class UIUtils {
    /**
     * 得到上下文
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 得到Resource对象
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到String.xml中的字符串，带占位符
     * @param resId
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        return getResource().getString(resId, formatArgs);
    }

    /**
     * 得到String.xml中的字符串数组
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId){
        return getResource().getStringArray(resId);
    }

    /**
     * 得到color.xml中的颜色
     * @param colorId
     * @return
     */
    public static int getColor(int colorId){
        return getResource().getColor(colorId);
    }

    /**
     * 得到应用程序的包名
     * @return
     */
    public static String getPackageName(){
        return getContext().getPackageName();
    }

    /**
     * 得到主线程的ID
     * @return
     */
    public static long getMainThreadId(){
        return BaseApplication.getMainThreadId();
    }

    public static Handler getMainThreadHandler(){
        return BaseApplication.getMainHandler();
    }

    /**
     * 安全开启线程
     * @param task
     */
    public static void postTaskSafely(Runnable task){
        int curThreadId = android.os.Process.myTid();//当前线程
        if(curThreadId == getMainThreadId()){
            //如果当前线程是主线程
            task.run();
        }else{
            //如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 延时开启任务
     * @param task
     * @param delayMillis
     */
    public static void postTaskDelay(Runnable task, int delayMillis){
       getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     * @param task
     */
    public static void removeTask(Runnable task){
        getMainThreadHandler().removeCallbacks(task);
    }

    /**
     * 获取系统版本
     * @return
     */
    public static int getVersion(){
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        return version;
    }
}
