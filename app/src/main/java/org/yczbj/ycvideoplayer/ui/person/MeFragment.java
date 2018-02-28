package org.yczbj.ycvideoplayer.ui.person;

import android.view.View;
import android.widget.TextView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;

import butterknife.Bind;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:MysteryCode
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }
}
