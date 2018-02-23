package org.yczbj.ycvideoplayer.base;

import org.yczbj.ycvideoplayer.ui.find.view.FindFragment;
import org.yczbj.ycvideoplayer.ui.home.view.fragment.HomeFragment;
import org.yczbj.ycvideoplayer.ui.me.view.MeFragment;
import org.yczbj.ycvideoplayer.ui.special.SpecialFragment;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/12/22
 * 描    述：Fragment工厂
 * 修订历史：
 *      备注：看《Android源码设计》一书，学习设计模式并运用
 * ================================================
 */
public class BaseFragmentFactory {

    private static BaseFragmentFactory mInstance;
    private HomeFragment mHomeFragment;
    private SpecialFragment mSpecialFragment;
    private FindFragment mFindFragment;
    private MeFragment mMeFragment;

    private BaseFragmentFactory() {}

    public static BaseFragmentFactory getInstance() {
        if (mInstance == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mInstance == null) {
                    mInstance = new BaseFragmentFactory();
                }
            }
        }
        return mInstance;
    }


    public HomeFragment getHomeFragment() {
        if (mHomeFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
            }
        }
        return mHomeFragment;
    }


    public SpecialFragment getSpecialFragment() {
        if (mSpecialFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mSpecialFragment == null) {
                    mSpecialFragment = new SpecialFragment();
                }
            }
        }
        return mSpecialFragment;
    }


    public FindFragment getFindFragment() {
        if (mFindFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mFindFragment == null) {
                    mFindFragment = new FindFragment();
                }
            }
        }
        return mFindFragment;
    }


    public MeFragment getMeFragment() {
        if (mMeFragment == null) {
            synchronized (BaseFragmentFactory.class) {
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                }
            }
        }
        return mMeFragment;
    }

}
