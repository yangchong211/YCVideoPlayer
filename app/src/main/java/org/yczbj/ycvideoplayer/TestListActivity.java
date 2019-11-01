package org.yczbj.ycvideoplayer;

import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;


/**
 * @author yc
 */
public class TestListActivity extends BaseActivity {

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test_fragment;
    }

    @Override
    public void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new TestListFragment())
                .commit();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}
