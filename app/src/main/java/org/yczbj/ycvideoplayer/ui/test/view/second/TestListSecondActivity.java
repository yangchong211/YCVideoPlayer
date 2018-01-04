package org.yczbj.ycvideoplayer.ui.test.view.second;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.base.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestListSecondActivity extends BaseActivity {

    List<TestListFragment> fragmentList = new ArrayList<>();
    @Bind(R.id.viewPager)
    ViewPager viewPager;


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test_list_second;
    }

    @Override
    public void initView() {
        fragmentList.add(new TestListFragment().setIndex(0));
        fragmentList.add(new TestListFragment().setIndex(1));
        fragmentList.add(new TestListFragment().setIndex(2));

        BasePagerAdapter myAdapter = new BasePagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                JZVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }


    @Override
    public void initListener() {

    }


    @Override
    public void initData() {

    }


}
