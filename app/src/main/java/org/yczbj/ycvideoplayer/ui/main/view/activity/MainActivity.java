package org.yczbj.ycvideoplayer.ui.main.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ns.yc.ycutilslib.activityManager.AppManager;
import com.ns.yc.ycutilslib.managerLeak.InputMethodManagerLeakUtils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.Constant;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.base.BaseFragmentFactory;
import org.yczbj.ycvideoplayer.download.TasksManager;
import org.yczbj.ycvideoplayer.ui.movie.view.fragment.MovieFragment;
import org.yczbj.ycvideoplayer.ui.news.NewsFragment;
import org.yczbj.ycvideoplayer.ui.video.view.fragment.VideoFragment;
import org.yczbj.ycvideoplayer.ui.home.view.fragment.HomeFragment;
import org.yczbj.ycvideoplayer.ui.main.contract.MainContract;
import org.yczbj.ycvideoplayer.ui.main.presenter.MainPresenter;
import org.yczbj.ycvideoplayer.ui.person.MeFragment;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import java.util.ArrayList;

import butterknife.BindView;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/11/18
 * 描    述：Main主页面
 * 修订历史：
 * ================================================
 */
public class MainActivity extends BaseActivity implements MainContract.View {


    @BindView(R.id.ctl_table)
    CommonTabLayout ctlTable;
    @BindView(R.id.fl_main)
    FrameLayout flMain;

    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "selectItem";
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MOVIE = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_ME = 3;
    private static final int FRAGMENT_NEWS = 4;
    private int position;

    private MainContract.Presenter presenter = new MainPresenter(this);
    private long exitTime;
    private long firstClickTime = 0;
    private Bundle savedInstanceState;
    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private MovieFragment mMovieFragment;
    private MeFragment meFragment;
    private NewsFragment newsFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
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
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // recreate 时记录当前位置 (在 Manifest 已禁止 Activity 旋转,所以旋转屏幕并不会执行以下代码)
        // 程序意外崩溃时保存状态信息
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, ctlTable.getCurrentTab());
    }


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        StateAppBar.setStatusBarLightMode(this, Color.WHITE);
        initTabLayout();
        initFragment();
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
                switch (position) {
                    case 0:
                        showFragment(FRAGMENT_HOME);
                        break;
                    case 1:
                        showFragment(FRAGMENT_NEWS);
                        doubleClick(FRAGMENT_NEWS);
                        break;
                    case 2:
                        showFragment(FRAGMENT_MOVIE);
                        doubleClick(FRAGMENT_MOVIE);
                        break;
                    case 3:
                        showFragment(FRAGMENT_VIDEO);
                        doubleClick(FRAGMENT_VIDEO);
                        break;
                    case 4:
                        showFragment(FRAGMENT_ME);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {}
        });
    }


    /**
     * 初始化ViewPager数据
     */
    private void initFragment() {
        if(savedInstanceState!=null){
            homeFragment = BaseFragmentFactory.getInstance().getHomeFragment();
            newsFragment = BaseFragmentFactory.getInstance().getNewsFragment();
            mMovieFragment = BaseFragmentFactory.getInstance().getMovieFragment();
            videoFragment = BaseFragmentFactory.getInstance().getVideoFragment();
            meFragment = BaseFragmentFactory.getInstance().getMeFragment();
            int index = savedInstanceState.getInt(POSITION);
            showFragment(index);
            ctlTable.setCurrentTab(savedInstanceState.getInt(SELECT_ITEM));
        }else {
            showFragment(FRAGMENT_HOME);
        }
    }


    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_HOME:
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (homeFragment == null) {
                    homeFragment = BaseFragmentFactory.getInstance().getHomeFragment();
                    ft.add(R.id.fl_main, homeFragment, HomeFragment.class.getName());
                } else {
                    ft.show(homeFragment);
                }
                break;
            case FRAGMENT_MOVIE:
                if (mMovieFragment == null) {
                    mMovieFragment = BaseFragmentFactory.getInstance().getMovieFragment();
                    ft.add(R.id.fl_main, mMovieFragment, MovieFragment.class.getName());
                } else {
                    ft.show(mMovieFragment);
                }
                break;
            case FRAGMENT_VIDEO:
                if (videoFragment == null) {
                    videoFragment = BaseFragmentFactory.getInstance().getVideoFragment();
                    ft.add(R.id.fl_main, videoFragment, VideoFragment.class.getName());
                } else {
                    ft.show(videoFragment);
                }
                break;
            case FRAGMENT_ME:
                if (meFragment == null) {
                    meFragment = BaseFragmentFactory.getInstance().getMeFragment();
                    ft.add(R.id.fl_main, meFragment, MeFragment.class.getName());
                } else {
                    ft.show(meFragment);
                }
                break;
            case FRAGMENT_NEWS:
                if (newsFragment == null) {
                    newsFragment = BaseFragmentFactory.getInstance().getNewsFragment();
                    ft.add(R.id.fl_main, newsFragment, MeFragment.class.getName());
                } else {
                    ft.show(newsFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }


    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (homeFragment != null) {
            setHide(ft,homeFragment);
        }
        if (newsFragment != null) {
            setHide(ft,newsFragment);
        }
        if (mMovieFragment != null) {
            setHide(ft,mMovieFragment);
        }
        if (videoFragment != null) {
            setHide(ft,videoFragment);
        }
        if (meFragment != null) {
            setHide(ft,meFragment);
        }
    }

    private void setHide(FragmentTransaction ft, Fragment fragment) {
        if(fragment.isAdded()){
            ft.hide(fragment);
        }
    }


    private void doubleClick(int index) {
        long secondClickTime = System.currentTimeMillis();
        if ((secondClickTime - firstClickTime < Constant.CLICK_TIME)) {
            switch (index) {
                case FRAGMENT_NEWS:
                    newsFragment.onDoubleClick();
                    break;
                case FRAGMENT_VIDEO:
                    videoFragment.onDoubleClick();
                    break;
                case FRAGMENT_MOVIE:

                    break;
                default:
                    break;
            }
        } else {
            firstClickTime = secondClickTime;
        }
    }


    @Override
    public Activity getActivity() {
        return this;
    }


    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShort("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                // 关闭文件下载
                TasksManager.getImpl().onDestroy();
                FileDownloader.getImpl().pauseAll();
                finish();
                AppManager.getAppManager().appExit(false);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
