package org.yczbj.ycvideoplayer.ui.person;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;


/**
 * @author yc
 */
public class TestSixVideoActivity extends BaseActivity  {

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) return;
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
                .add(R.id.container, new TestSixVideoFragment())
                .commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}
