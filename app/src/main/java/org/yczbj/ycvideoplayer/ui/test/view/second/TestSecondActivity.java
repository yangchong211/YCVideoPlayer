package org.yczbj.ycvideoplayer.ui.test.view.second;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.view.second.TestListFirstActivity;
import org.yczbj.ycvideoplayer.ui.test.view.second.TestListFiveActivity;
import org.yczbj.ycvideoplayer.ui.test.view.second.TestListFourActivity;
import org.yczbj.ycvideoplayer.ui.test.view.second.TestListSecondActivity;
import org.yczbj.ycvideoplayer.ui.test.view.second.TestListThirdActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class TestSecondActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.normal)
    Button normal;
    @Bind(R.id.listview_fragment_viewpager)
    Button listviewFragmentViewpager;
    @Bind(R.id.multiholder)
    Button multiholder;
    @Bind(R.id.recyleview)
    Button recyleview;
    @Bind(R.id.recyleview_type)
    Button recyleviewType;

    @Override
    public int getContentView() {
        return R.layout.activity_test_second;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        normal.setOnClickListener(this);
        listviewFragmentViewpager.setOnClickListener(this);
        multiholder.setOnClickListener(this);
        recyleview.setOnClickListener(this);
        recyleviewType.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normal:
                startActivity(TestListFirstActivity.class);
                break;
            case R.id.listview_fragment_viewpager:
                startActivity(TestListSecondActivity.class);
                break;
            case R.id.multiholder:
                startActivity(TestListThirdActivity.class);
                break;
            case R.id.recyleview:
                startActivity(TestListFourActivity.class);
                break;
            case R.id.recyleview_type:
                startActivity(TestListFiveActivity.class);
                break;
        }
    }


}
