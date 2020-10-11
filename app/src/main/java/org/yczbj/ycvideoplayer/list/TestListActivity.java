package org.yczbj.ycvideoplayer.list;

import org.yczbj.ycvideoplayer.BaseActivity;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.tiktok.TikTokListFragment;
import org.yczbj.ycvideoplayerlib.old.other.VideoPlayerManager;


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
        int type = getIntent().getIntExtra("type", 0);
        if (type==0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new RecyclerViewFragment())
                    .commit();
        } else if (type==1){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new RecyclerViewAutoPlayFragment())
                    .commit();
        } else if (type==2){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new TikTokListFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new SeamlessPlayFragment())
                    .commit();
        }

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

}
