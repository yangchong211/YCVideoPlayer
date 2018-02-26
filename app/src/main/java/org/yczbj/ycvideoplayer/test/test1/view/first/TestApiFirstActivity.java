package org.yczbj.ycvideoplayer.test.test1.view.first;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.test.test1.model.VideoConstant;

import butterknife.Bind;
import cn.jzvd.CustomView.JZVideoPlayerStandardAutoCompleteAfterFullscreen;
import cn.jzvd.CustomView.JZVideoPlayerStandardShowShareButtonAfterFullscreen;
import cn.jzvd.CustomView.JZVideoPlayerStandardShowTextureViewAfterAutoComplete;
import cn.jzvd.CustomView.JZVideoPlayerStandardShowTitleAfterFullscreen;
import cn.jzvd.JZVideoPlayer;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestApiFirstActivity extends BaseActivity {

    @Bind(R.id.standard_with_share)
    JZVideoPlayerStandardShowShareButtonAfterFullscreen standardWithShare;
    @Bind(R.id.standard_show_title_after_fullscreen)
    JZVideoPlayerStandardShowTitleAfterFullscreen standardShowTitleAfterFullscreen;
    @Bind(R.id.standard_show_auto_complete)
    JZVideoPlayerStandardShowTextureViewAfterAutoComplete standardShowAutoComplete;
    @Bind(R.id.standard_auto_complete)
    JZVideoPlayerStandardAutoCompleteAfterFullscreen standardAutoComplete;
    @Bind(R.id.video_1_1)
    JZVideoPlayerStandardAutoCompleteAfterFullscreen video11;
    @Bind(R.id.video_16_9)
    JZVideoPlayerStandardAutoCompleteAfterFullscreen video169;

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
        return R.layout.activity_test_api_first;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        standardWithShare = (JZVideoPlayerStandardShowShareButtonAfterFullscreen)findViewById(R.id.standard_with_share);
        standardWithShare.setUp(VideoConstant.videoUrlList[3], JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "饺子想呼吸");
        Picasso.with(this)
                .load(VideoConstant.videoThumbList[3])
                .into(standardWithShare.thumbImageView);


        standardShowTitleAfterFullscreen = (JZVideoPlayerStandardShowTitleAfterFullscreen) findViewById(R.id.standard_show_title_after_fullscreen);
        standardShowTitleAfterFullscreen.setUp(VideoConstant.videoUrlList[4], JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "饺子想摇头");
        Picasso.with(this)
                .load(VideoConstant.videoThumbList[4])
                .into(standardShowTitleAfterFullscreen.thumbImageView);

        standardShowAutoComplete = (JZVideoPlayerStandardShowTextureViewAfterAutoComplete) findViewById(R.id.standard_show_auto_complete);
        standardShowAutoComplete.setUp(VideoConstant.videoUrlList[5], JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "饺子想旅行");
        Picasso.with(this)
                .load(VideoConstant.videoThumbList[5])
                .into(standardShowAutoComplete.thumbImageView);

        standardAutoComplete = (JZVideoPlayerStandardAutoCompleteAfterFullscreen) findViewById(R.id.standard_auto_complete);
        standardAutoComplete.setUp(VideoConstant.videoUrls[0][1], JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "饺子没来");
        Picasso.with(this)
                .load(VideoConstant.videoThumbs[0][1])
                .into(standardAutoComplete.thumbImageView);

        video11 = (JZVideoPlayerStandardAutoCompleteAfterFullscreen) findViewById(R.id.video_1_1);
        video11.setUp(VideoConstant.videoUrls[0][1], JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "饺子有事吗");
        Picasso.with(this)
                .load(VideoConstant.videoThumbs[0][1])
                .into(video11.thumbImageView);
        video11.widthRatio = 1;
        video11.heightRatio = 1;

        video169 = (JZVideoPlayerStandardAutoCompleteAfterFullscreen) findViewById(R.id.video_16_9);
        video169.setUp(VideoConstant.videoUrls[0][1], JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "饺子来不了");
        Picasso.with(this)
                .load(VideoConstant.videoThumbs[0][1])
                .into(video169.thumbImageView);
        video169.widthRatio = 16;
        video169.heightRatio = 9;
    }


}
