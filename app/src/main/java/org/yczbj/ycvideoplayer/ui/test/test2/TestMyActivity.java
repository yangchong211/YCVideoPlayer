package org.yczbj.ycvideoplayer.ui.test.test2;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyFirstActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyFiveActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyFourActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMySecondActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMySixActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyThirdActivity;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.utils.VideoPlayerUtils;

import butterknife.Bind;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * @author yc
 * @date 2017/12/29
 * 注意：在对应的播放Activity页面，清单文件中一定要添加
 * android:configChanges="orientation|keyboardHidden|screenSize"
 * android:screenOrientation="portrait"
 */

public class TestMyActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.btn_my_1)
    Button btnMy1;
    @Bind(R.id.btn_my_2)
    Button btnMy2;
    @Bind(R.id.btn_my_3)
    Button btnMy3;
    @Bind(R.id.btn_my_4)
    Button btnMy4;
    @Bind(R.id.btn_my_5)
    Button btnMy5;
    @Bind(R.id.btn_my_6)
    Button btnMy6;


    private String path = ConstantVideo.VideoPlayerList[7];

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



    @Override
    public int getContentView() {
        return R.layout.activity_test_my;
    }


    @Override
    public void initView() {
        YCAppBar.setStatusBarLightMode(this, Color.WHITE);
    }



    @Override
    public void initListener() {
        btnMy1.setOnClickListener(this);
        btnMy2.setOnClickListener(this);
        btnMy3.setOnClickListener(this);
        btnMy4.setOnClickListener(this);
        btnMy5.setOnClickListener(this);
        btnMy6.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_1:
                startActivity(TestMyFirstActivity.class);
                break;
            case R.id.btn_my_2:
                startActivity(TestMySecondActivity.class);
                break;
            case R.id.btn_my_3:
                startActivity(TestMyThirdActivity.class);
                break;
            case R.id.btn_my_4:
                startActivity(TestMyFourActivity.class);
                break;
            case R.id.btn_my_5:
                startActivity(TestMyFiveActivity.class);
                break;
            case R.id.btn_my_6:
                startActivity(TestMySixActivity.class);
                break;
            default:
                break;
        }
    }

}
