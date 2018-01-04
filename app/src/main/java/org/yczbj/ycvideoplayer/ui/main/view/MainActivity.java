package org.yczbj.ycvideoplayer.ui.main.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ns.yc.ycutilslib.managerLeak.InputMethodManagerLeakUtils;
import com.ns.yc.ycutilslib.viewPager.NoSlidingViewPager;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.base.BasePagerAdapter;
import org.yczbj.ycvideoplayer.ui.find.FindFragment;
import org.yczbj.ycvideoplayer.ui.home.view.HomeFragment;
import org.yczbj.ycvideoplayer.ui.me.MeFragment;
import org.yczbj.ycvideoplayer.ui.main.contract.MainContract;
import org.yczbj.ycvideoplayer.ui.main.presenter.MainPresenter;
import org.yczbj.ycvideoplayer.ui.special.SpecialFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/11/18
 * 描    述：Main主页面
 * 修订历史：
 * ================================================
 */
public class MainActivity extends BaseActivity implements MainContract.View{


    @Bind(R.id.vp_home)
    NoSlidingViewPager vpHome;
    @Bind(R.id.ctl_table)
    CommonTabLayout ctlTable;

    private MainContract.Presenter presenter = new MainPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.bindView(this);
        presenter.subscribe();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManagerLeakUtils.fixInputMethodManagerLeak(this);
        presenter.unSubscribe();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initTabLayout();
        initViewPager();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    /**
     * 初始化底部导航栏数据
     */
    private void initTabLayout() {
        ArrayList<CustomTabEntity> mTabEntities = presenter.getTabEntity();
        ctlTable.setTabData(mTabEntities);
        //ctlTable.showDot(3);                   //显示红点
        //ctlTable.showMsg(2,5);                 //显示未读信息
        //ctlTable.showMsg(1,3);                 //显示未读信息
        //ctlTable.setMsgMargin(1, 2, 2);        //显示红点信息位置
        ctlTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpHome.setCurrentItem(position);
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


    /**
     * 初始化ViewPager数据
     */
    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        SpecialFragment specialFragment = new SpecialFragment();
        FindFragment findFragment = new FindFragment();
        MeFragment meFragment = new MeFragment();
        fragments.add(homeFragment);
        fragments.add(specialFragment);
        fragments.add(findFragment);
        fragments.add(meFragment);

        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager(), fragments);
        vpHome.setAdapter(adapter);
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ctlTable.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        vpHome.setOffscreenPageLimit(4);
        vpHome.setCurrentItem(0);
    }


    @Override
    public Activity getActivity() {
        return this;
    }



}
