package com.zyn.sudu.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zyn.sudu.R;
import com.zyn.sudu.base.BaseApplication;
import com.zyn.sudu.logics.Game;
import com.zyn.sudu.utils.DialogUtils;
import com.zyn.sudu.utils.MyContant;
import com.zyn.sudu.utils.SharedPreferencesUtils;
import com.zyn.sudu.utils.UIUtils;

/**
 * Author:ZengYinan zengyinanos@qq.com
 * Date:2016/7/3
 * Time:15:36
 * Desc:
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNewgame;
    private Button btnContinue;
    private Button btnDifficulty;
    private Button btnRank;
    private ImageView ivExit;
    private long clickTime = Integer.MIN_VALUE;
    private String gameData;
    private String[] diff = new String[]{"简单", "中等", "复杂"};
    private int[] diffInt = new int[]{30, 50, 70};

    private void assignViews() {
        btnNewgame = (Button) findViewById(R.id.btn_newgame);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnDifficulty = (Button) findViewById(R.id.btn_difficulty);
        btnRank = (Button) findViewById(R.id.btn_rank);
        ivExit = (ImageView) findViewById(R.id.iv_exit);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        BaseApplication.addActivity(this);
        setListener();
    }

    private void setListener() {
        btnNewgame.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnDifficulty.setOnClickListener(this);
        btnRank.setOnClickListener(this);
        ivExit.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_game);
        assignViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_newgame:
                gameData = SharedPreferencesUtils.getString(UIUtils.getContext(), MyContant.CONTINUEGAME, "");
                //开始新游戏
                if (gameData.isEmpty()) {
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    startActivity(mainIntent);
                } else {
                    //表示有数据
                    Dialog dialog = DialogUtils.showAlert(this, "数独", "发现您还有存档，继续新游戏会覆盖存档哦，是否继续", "开始新游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        }
                    }, "继续存档游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
                            mainIntent.putExtra(MyContant.CONTINUEGAME, gameData);
                            startActivity(mainIntent);
                        }
                    });
                    dialog.show();
                }
                break;
            case R.id.btn_continue:
                //继续游戏
                gameData = SharedPreferencesUtils.getString(UIUtils.getContext(), MyContant.CONTINUEGAME, "");
                continueGame();
                break;
            case R.id.btn_difficulty:
                showSelectDiffDialog();
                break;
            case R.id.btn_rank:
                ShowMessageDialog();
                break;
            case R.id.iv_exit:
                Dialog dialog = DialogUtils.showAlert(this, "数独", "真的要退出了吗", "退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        BaseApplication.exit();
                    }
                }, "再玩一会儿", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void ShowMessageDialog() {
        Dialog dialog = DialogUtils.showPrompt(GameActivity.this, "数独","数独（すうどく，Sūdoku），是源自18世纪瑞士发明，流传到美国，再由日本发扬光大的一种数学游戏。是一种运用纸、笔进行演算的逻辑游戏。玩家需要根据9×9盘面上的已知数字，推理出所有剩余空格的数字，并满足每一行、每一列、每一个粗线宫内的数字均含1-9，不重复。","知道啦");
        dialog.show();
    }

    private void showSelectDiffDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("数独");
        final int count = SharedPreferencesUtils.getInt(this, MyContant.COUNT, 30);

        int type = 0;
        if (count == 30) {
            type = 0;
        } else if (count == 50) {
            type = 1;
        } else if (count == 70) {
            type = 2;
        }

        builder.setSingleChoiceItems(diff, type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferencesUtils.saveInt(GameActivity.this, MyContant.COUNT, diffInt[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void continueGame() {
        if (gameData.isEmpty()) {
            //为空，说明还没有存档
            Dialog dialog = DialogUtils.showPrompt(this, "数独", "您还没有存档哟", "返回");
            dialog.show();
        } else {
            //有数据，继续游戏
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(MyContant.CONTINUEGAME, gameData);//把存的数据传过去
            startActivity(mainIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - clickTime < 2000) {
                BaseApplication.exit();
            } else {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                clickTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
