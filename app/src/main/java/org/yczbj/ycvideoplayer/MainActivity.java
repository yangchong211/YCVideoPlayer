package org.yczbj.ycvideoplayer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.yczbj.ycvideoplayer.activity.TypeActivity;
import org.yczbj.ycvideoplayer.list.TestListActivity;
import org.yczbj.ycvideoplayer.surface.TestSurfaceActivity;
import org.yczbj.ycvideoplayerlib.tool.manager.VideoPlayerManager;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/11/18
 * 描    述：Main主页面
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long exitTime;
    private Toolbar mToolbar;
    private TextView mTv0;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;
    private TextView mTv9;
    private TextView mTv10;
    private TextView mTv11;
    private TextView mTv12;
    private TextView mTv13;
    private TextView mTv14;
    private TextView mTv15;
    private TextView mTv16;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        StateAppBar.setStatusBarLightMode(this, Color.WHITE);
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTv0 = (TextView) findViewById(R.id.tv_0);
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mTv6 = (TextView) findViewById(R.id.tv_6);
        mTv7 = (TextView) findViewById(R.id.tv_7);
        mTv8 = (TextView) findViewById(R.id.tv_8);
        mTv9 = (TextView) findViewById(R.id.tv_9);
        mTv10 = (TextView) findViewById(R.id.tv_10);
        mTv11 = (TextView) findViewById(R.id.tv_11);
        mTv12 = (TextView) findViewById(R.id.tv_12);
        mTv13 = (TextView) findViewById(R.id.tv_13);
        mTv14 = (TextView) findViewById(R.id.tv_14);
        mTv15 = (TextView) findViewById(R.id.tv_15);
        mTv16 = (TextView) findViewById(R.id.tv_16);

        mTv0.setOnClickListener(this);
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
        mTv5.setOnClickListener(this);
        mTv6.setOnClickListener(this);
        mTv7.setOnClickListener(this);
        mTv8.setOnClickListener(this);
        mTv9.setOnClickListener(this);
        mTv10.setOnClickListener(this);
        mTv11.setOnClickListener(this);
        mTv12.setOnClickListener(this);
        mTv13.setOnClickListener(this);
        mTv14.setOnClickListener(this);
        mTv15.setOnClickListener(this);
        mTv16.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_0:
                startActivity(TypeActivity.class);
                break;
            case R.id.tv_1:
                startActivity(TestNormalActivity.class);
                break;
            case R.id.tv_2:
                startActivity(TestFullActivity2.class);
                break;
            case R.id.tv_3:
                startActivity(TestTinyActivity.class);
                break;
            case R.id.tv_4:
                startActivity(TestWindowActivity.class);
                break;
            case R.id.tv_5:
                startActivity(TestClarityActivity.class);
                break;
            case R.id.tv_6:
                startActivity(TestFragmentActivity.class);
                break;
            case R.id.tv_7:
                startActivity(TestRecyclerActivity.class);
                break;
            case R.id.tv_8:
                startActivity(TestEightVideoActivity.class);
                break;
            case R.id.tv_9:
                startActivity(TestListActivity.class);
                break;
            case R.id.tv_10:

                break;
            case R.id.tv_11:
                startActivity(TestWindowActivity.class);
                break;
            case R.id.tv_12:
                startActivity(TestSavePosActivity.class);
                break;
            case R.id.tv_13:
                startActivity(TestSurfaceActivity.class);
                break;
            case R.id.tv_14:
                break;
            case R.id.tv_15:
                break;
            case R.id.tv_16:
                break;
            default:
                break;
        }
    }

    private void startActivity(Class c){
        startActivity(new Intent(this,c));
    }
}
