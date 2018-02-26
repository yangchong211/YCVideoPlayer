package org.yczbj.ycvideoplayer.test.test1.view.six;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;

import butterknife.Bind;
import cn.jzvd.CustomView.JZVideoPlayerStandardAutoCompleteAfterFullscreen;
import cn.jzvd.CustomView.JZVideoPlayerStandardShowTextureViewAfterAutoComplete;
import cn.jzvd.CustomView.JZVideoPlayerStandardShowTitleAfterFullscreen;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class TestSixActivity extends BaseActivity {


    @Bind(R.id.jz_video_1)
    JZVideoPlayerStandard jzVideo1;
    @Bind(R.id.jz_video_2)
    JZVideoPlayerStandardShowTitleAfterFullscreen jzVideo2;
    @Bind(R.id.jz_video_3)
    JZVideoPlayerStandardShowTextureViewAfterAutoComplete jzVideo3;
    @Bind(R.id.jz_video_4)
    JZVideoPlayerStandardAutoCompleteAfterFullscreen jzVideo4;
    @Bind(R.id.video_1_1)
    JZVideoPlayerStandardAutoCompleteAfterFullscreen video11;
    @Bind(R.id.video_16_9)
    JZVideoPlayerStandardAutoCompleteAfterFullscreen video169;


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.clearSavedProgress(this, null);
        //home back
        JZVideoPlayer.goOnPlayOnPause();
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_six;
    }


    @Override
    public void initView() {
        jzVideo1.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo1.thumbImageView);


        jzVideo2.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo2.thumbImageView);


        jzVideo3.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo3.thumbImageView);


        jzVideo4.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo4.thumbImageView);


        video11.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(video11.thumbImageView);
        video11.widthRatio = 1;
        video11.heightRatio = 1;


        video169.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(video169.thumbImageView);
        video169.widthRatio = 16;
        video169.heightRatio = 9;
    }


    @Override
    public void initListener() {

    }


    @Override
    public void initData() {

    }



}
