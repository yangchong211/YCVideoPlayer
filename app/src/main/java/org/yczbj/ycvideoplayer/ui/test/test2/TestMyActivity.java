package org.yczbj.ycvideoplayer.ui.test.test2;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.person.TestThirdVideoActivity;
import org.yczbj.ycvideoplayer.ui.person.TestEightVideoActivity;
import org.yczbj.ycvideoplayer.ui.person.TestSixVideoActivity;
import org.yczbj.ycvideoplayer.ui.person.TestFiveVideoActivity;
import org.yczbj.ycvideoplayer.ui.person.TestNineVideoActivity;
import org.yczbj.ycvideoplayer.ui.person.TestSevenVideoActivity;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import butterknife.BindView;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

/**
 * @author yc
 * @date 2017/12/29
 * 注意：在对应的播放Activity页面，清单文件中一定要添加
 * android:configChanges="orientation|keyboardHidden|screenSize"
 * android:screenOrientation="portrait"
 */

public class TestMyActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.btn_my_1)
    Button btnMy1;
    @BindView(R.id.btn_my_2)
    Button btnMy2;
    @BindView(R.id.btn_my_3)
    Button btnMy3;
    @BindView(R.id.btn_my_4)
    Button btnMy4;
    @BindView(R.id.btn_my_5)
    Button btnMy5;
    @BindView(R.id.btn_my_6)
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
        StateAppBar.setStatusBarLightMode(this, Color.WHITE);
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
                startActivity(TestThirdVideoActivity.class);
                break;
            case R.id.btn_my_2:
                startActivity(TestFiveVideoActivity.class);
                break;
            case R.id.btn_my_3:
                startActivity(TestSevenVideoActivity.class);
                break;
            case R.id.btn_my_4:
                startActivity(TestSixVideoActivity.class);
                break;
            case R.id.btn_my_5:
                startActivity(TestEightVideoActivity.class);
                break;
            case R.id.btn_my_6:
                startActivity(TestNineVideoActivity.class);
                break;
            default:
                break;
        }
    }

}
