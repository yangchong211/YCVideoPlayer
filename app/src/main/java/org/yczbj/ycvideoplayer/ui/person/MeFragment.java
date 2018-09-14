package org.yczbj.ycvideoplayer.ui.person;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;
import org.yczbj.ycvideoplayer.weight.AboutMeDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:MysteryCode
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_3)
    TextView tv3;
    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        tv3.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_3:
                break;
            default:
                break;
        }
    }


}
