package com.zyn.sudu.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/6/29
 * Time:20:04
 * Desc:
 */
public class BaseApplication extends Application {
    private static Context context;
    private static Thread mainThread;
    private static long mainThreadId;
    private static Looper mainThreadLooper;
    private static Handler mainHandler;

    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static long getMainThreadId() {
        return mainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mainThreadLooper;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        mainThreadLooper = getMainThreadLooper();
        mainHandler = new Handler();
        super.onCreate();
    }

    /*------------------------------下面这段代码是为了退出程序------------------------------*/
    private static List<Activity> mList = new LinkedList();

    // add Activity
    public static void addActivity(Activity activity) {
        mList.add(activity);
    }

    public static void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
