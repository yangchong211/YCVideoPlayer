package org.yczbj.ycvideoplayer.ui.person;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;

import butterknife.Bind;


public class MeFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.tv_3)
    TextView tv3;
    @Bind(R.id.tv_4)
    TextView tv4;
    @Bind(R.id.tv_5)
    TextView tv5;
    @Bind(R.id.tv_6)
    TextView tv6;
    @Bind(R.id.tv_7)
    TextView tv7;
    @Bind(R.id.tv_8)
    TextView tv8;
    @Bind(R.id.tv_9)
    TextView tv9;
    @Bind(R.id.tv_10)
    TextView tv10;
    @Bind(R.id.tv_11)
    TextView tv11;
    @Bind(R.id.tv_12)
    TextView tv12;
    @Bind(R.id.tv_13)
    TextView tv13;
    @Bind(R.id.tv_14)
    TextView tv14;
    @Bind(R.id.tv_15)
    TextView tv15;
    @Bind(R.id.tv_16)
    TextView tv16;

    @Override
    public int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        tv11.setOnClickListener(this);
        tv12.setOnClickListener(this);
        tv13.setOnClickListener(this);
        tv14.setOnClickListener(this);
        tv15.setOnClickListener(this);
        tv16.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                startActivity(TestFirstVideoActivity.class);
                break;
            case R.id.tv_2:
                startActivity(TestSecondFullActivity.class);
                break;
            case R.id.tv_3:
                startActivity(TestThirdVideoActivity.class);
                break;
            case R.id.tv_4:
                startActivity(TestFourWindowActivity.class);
                break;
            case R.id.tv_5:
                startActivity(TestFiveVideoActivity.class);
                break;
            case R.id.tv_6:
                startActivity(TestSixVideoActivity.class);
                break;
            case R.id.tv_7:
                startActivity(TestSevenVideoActivity.class);
                break;
            case R.id.tv_8:
                startActivity(TestEightVideoActivity.class);
                break;
            case R.id.tv_9:
                startActivity(TestNineVideoActivity.class);
                break;
            case R.id.tv_10:
                startActivity(VideoPlayerMeActivity.class);
                break;
            case R.id.tv_11:
                startActivity(TestFourWindowActivity.class);
                break;
            case R.id.tv_12:
                break;
            case R.id.tv_13:
                break;
            case R.id.tv_14:
                break;
            case R.id.tv_15:
                break;
            case R.id.tv_16:
                break;
            default:
                break;
        }
    }


}
