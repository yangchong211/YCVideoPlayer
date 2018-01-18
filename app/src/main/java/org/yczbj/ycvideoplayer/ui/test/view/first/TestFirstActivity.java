package org.yczbj.ycvideoplayer.ui.test.view.first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.model.VideoConstant;

import java.util.HashMap;
import java.util.LinkedHashMap;

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

public class TestFirstActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.btn_4)
    Button btn4;
    @Bind(R.id.btn_5)
    Button btn5;
    @Bind(R.id.btn_6)
    Button btn6;


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
        return R.layout.activity_test_first;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initView() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("高清", VideoConstant.videoUrls[0][9]);
        map.put("标清", VideoConstant.videoUrls[0][6]);
        map.put("普清", VideoConstant.videoUrlList[0]);
        Object[] objects = new Object[3];
        objects[0] = map;
        objects[1] = false;
        objects[2] = new HashMap<>();
        ((HashMap) objects[2]).put("key", "value");
        jzVideo.setUp(objects, 2
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子不信");
        Picasso.with(this)
                .load(VideoConstant.videoThumbList[0])
                .into(jzVideo.thumbImageView);
    }

    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                startActivity(TestApiFirstActivity.class);
                break;
            case R.id.btn_2:

                break;
            case R.id.btn_3:
                startActivity(TestApiThreeActivity.class);
                break;
            case R.id.btn_4:
                startActivity(TestApiFourActivity.class);
                break;
            case R.id.btn_5:
                startActivity(TestApiFiveActivity.class);
                break;
            case R.id.btn_6:
                startActivity(TestApiSixActivity.class);
                break;
            default:
                break;
        }
    }
}
