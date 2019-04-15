package org.yczbj.ycvideoplayer.ui.person;

import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoControlListener;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.dialog.VideoClarity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author yc
 */
public class TestFiveVideoActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.nice_video_player)
    VideoPlayer mVideoPlayer;
    @BindView(R.id.btn_tiny_1)
    Button btnTiny1;
    @BindView(R.id.btn_tiny_2)
    Button btnTiny2;

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
        return R.layout.activity_test_video_third;
    }

    @Override
    public void initView() {
        mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("Beautiful China...");
        controller.setLength(117000);
        controller.setClarity(getClarites(), 0);
        Glide.with(this)
                .load("http://imgsrc.baidu.com/image/c0%3Dshijue%2C0%2C0%2C245%2C40/sign=304dee3ab299a9012f38537575fc600e/91529822720e0cf3f8b77cd50046f21fbe09aa5f.jpg")
                .placeholder(R.drawable.image_default)
                .crossFade()
                .into(controller.imageView());
        mVideoPlayer.setController(controller);
    }

    @Override
    public void initListener() {
        btnTiny1.setText("1. 全屏播放时可以切换清晰度");
        btnTiny2.setText("2. 全屏播放");
        btnTiny1.setOnClickListener(this);
        btnTiny2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }



    public List<VideoClarity> getClarites() {
        List<VideoClarity> clarities = new ArrayList<>();
        clarities.add(new VideoClarity("标清", "270P", "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new VideoClarity("高清", "480P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new VideoClarity("超清", "720P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new VideoClarity("蓝光", "1080P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        return clarities;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tiny_1:

                break;
            case R.id.btn_tiny_2:

                break;
            default:
                break;
        }
    }
}
