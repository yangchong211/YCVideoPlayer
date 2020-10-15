package org.yczbj.ycvideoplayer.newPlayer.list;

import org.yczbj.ycvideoplayer.BaseActivity;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.newPlayer.tiktok.TikTok1ListFragment;
import org.yczbj.ycvideoplayer.newPlayer.tiktok.TikTokListFragment;
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
        } else if (type==3){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new TikTok1ListFragment())
                    .commit();
        }else if (type==4){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new SeamlessPlayFragment())
                    .commit();
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new RecyclerView2Fragment())
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
