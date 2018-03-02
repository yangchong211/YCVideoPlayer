package org.yczbj.ycvideoplayer.ui.test.test1.view.first;

import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.model.VideoConstant;

import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestApiFiveActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    @Bind(R.id.rotation_to_90)
    Button rotationTo90;
    @Bind(R.id.video_image_display_fill_parent)
    Button videoImageDisplayFillParent;
    @Bind(R.id.video_image_display_fill_crop)
    Button videoImageDisplayFillCrop;
    @Bind(R.id.video_image_diaplay_original)
    Button videoImageDiaplayOriginal;


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
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
        return R.layout.activity_test_api_five;
    }

    @Override
    public void initView() {
        jzVideo.setUp(VideoConstant.videoUrls[0][7]
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, VideoConstant.videoTitles[0][7]);
        Picasso.with(this)
                .load(VideoConstant.videoThumbs[0][7])
                .into(jzVideo.thumbImageView);
        // The Point IS
        jzVideo.videoRotation = 180;
    }

    @Override
    public void initListener() {
        rotationTo90.setOnClickListener(this);
        videoImageDisplayFillParent.setOnClickListener(this);
        videoImageDisplayFillCrop.setOnClickListener(this);
        videoImageDiaplayOriginal.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rotation_to_90:
                JZVideoPlayer.setTextureViewRotation(90);
                break;
            case R.id.video_image_display_fill_parent:
                JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
                break;
            case R.id.video_image_display_fill_crop:
                JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
                break;
            case R.id.video_image_diaplay_original:
                JZVideoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL);
                break;
            default:

                break;
        }
    }

}
