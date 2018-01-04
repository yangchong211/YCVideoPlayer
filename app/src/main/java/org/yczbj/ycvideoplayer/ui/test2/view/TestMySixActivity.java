package org.yczbj.ycvideoplayer.ui.test2.view;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;


public class TestMySixActivity extends BaseActivity {

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test_my_four;
    }

    @Override
    public void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new TestMySixFragment())
                .commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}
