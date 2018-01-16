package org.yczbj.ycvideoplayer.ui.me.view;

import android.view.View;
import android.widget.TextView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by yc on 2018/1/15.
 */

public class MeMemberActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_fail)
    TextView tvFail;
    @Bind(R.id.tv_success)
    TextView tvSuccess;

    @Override
    public int getContentView() {
        return R.layout.activity_me_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        tvFail.setOnClickListener(this);
        tvSuccess.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_fail:

                break;
            case R.id.tv_success:

                break;
            default:
                break;
        }
    }
}
