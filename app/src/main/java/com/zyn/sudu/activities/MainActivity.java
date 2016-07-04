package com.zyn.sudu.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zyn.sudu.base.BaseApplication;
import com.zyn.sudu.utils.MyContant;
import com.zyn.sudu.utils.SharedPreferencesUtils;
import com.zyn.sudu.utils.UIUtils;
import com.zyn.sudu.views.ShuduView;

public class MainActivity extends AppCompatActivity {

    private ShuduView shuduView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String gameData = getIntent().getStringExtra(MyContant.CONTINUEGAME);
        shuduView = new ShuduView(this, null, gameData);
        setContentView(shuduView);
        BaseApplication.addActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            int[] gameData = shuduView.game.getSudoku();
            //把数据转换成String类型存储起来
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < gameData.length; i++) {
                stringBuilder.append(String.valueOf(gameData[i]));
            }
            //存储
            SharedPreferencesUtils.saveString(UIUtils.getContext(), MyContant.CONTINUEGAME, stringBuilder.toString());
        }
        return super.onKeyDown(keyCode, event);
    }

}
