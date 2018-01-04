package org.yczbj.ycvideoplayer.ui.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseFragment;
import org.yczbj.ycvideoplayer.ui.test.TestActivity;
import org.yczbj.ycvideoplayer.ui.test2.TestMyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author yc
 * @date 2017/12/29
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_1)
    TextView tv1;
    @Bind(R.id.tv_2)
    TextView tv2;

    @Override
    public int getContentView() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                startActivity(TestActivity.class);
                break;
            case R.id.tv_2:
                startActivity(TestMyActivity.class);
                break;
            default:
                break;
        }
    }

}
