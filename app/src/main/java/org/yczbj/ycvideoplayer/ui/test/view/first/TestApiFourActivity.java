package org.yczbj.ycvideoplayer.ui.test.view.first;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.model.VideoConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestApiFourActivity extends BaseActivity {

    @Bind(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @Bind(R.id.drawer_layout)
    LinearLayout drawerLayout;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test_api_four;
    }

    @Override
    public void initView() {
        videoPlayer.setUp(VideoConstant.videoUrlList[0]
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
        Picasso.with(this)
                .load(VideoConstant.videoThumbList[0])
                .into(videoPlayer.thumbImageView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


}
