package org.yczbj.ycvideoplayer.ui.test.test1.view.three;

import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;

import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class TestThreeActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    @Bind(R.id.tiny_window)
    Button tinyWindow;
    @Bind(R.id.auto_tiny_list_view)
    Button autoTinyListView;
    @Bind(R.id.auto_tiny_list_view_multi_holder)
    Button autoTinyListViewMultiHolder;
    @Bind(R.id.auto_tiny_list_view_recycleview)
    Button autoTinyListViewRecycleview;
    @Bind(R.id.auto_tiny_list_view_recycleview_multiholder)
    Button autoTinyListViewRecycleviewMultiholder;


    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
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
        return R.layout.activity_test_three;
    }


    @Override
    public void initView() {
        jzVideo.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo.thumbImageView);

    }

    @Override
    public void initListener() {
        tinyWindow.setOnClickListener(this);
        autoTinyListView.setOnClickListener(this);
        autoTinyListViewMultiHolder.setOnClickListener(this);
        autoTinyListViewRecycleview.setOnClickListener(this);
        autoTinyListViewRecycleviewMultiholder.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tiny_window:
                jzVideo.startWindowTiny();
                break;
            case R.id.auto_tiny_list_view:
                startActivity(TestTinyFirstActivity.class);
                break;
            case R.id.auto_tiny_list_view_multi_holder:
                startActivity(TestTinyThirdActivity.class);
                break;
            case R.id.auto_tiny_list_view_recycleview:
                startActivity(TestTinyFourActivity.class);
                break;
            case R.id.auto_tiny_list_view_recycleview_multiholder:
                startActivity(TestTinyFiveActivity.class);
                break;
            default:
                break;
        }
    }

}
