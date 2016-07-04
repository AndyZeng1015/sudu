package com.zyn.sudu.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ant.liao.GifView;
import com.zyn.sudu.R;
import com.zyn.sudu.base.BaseApplication;
import com.zyn.sudu.utils.DensityUtil;
import com.zyn.sudu.utils.ThreadPoolUtils;
import com.zyn.sudu.utils.UIUtils;

import java.io.File;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/7/3
 * Time:10:09
 * Desc:
 */
public class SplashActivity extends AppCompatActivity{

    private GifView gf_welcome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        BaseApplication.addActivity(this);
        startThread();
    }

    private void startThread() {
        ThreadPoolUtils.getInstance().addTask(new MyTask());
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        gf_welcome = (GifView) findViewById(R.id.gf_welcome);
        gf_welcome.setGifImage(R.drawable.sudoku);
        gf_welcome.setShowDimension(500,400);
    }

    /**
     * 睡眠后跳转
     */
    class MyTask implements Runnable{

        @Override
        public void run() {
            SystemClock.sleep(2000);
            Intent intent = new Intent(SplashActivity.this, GameActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
