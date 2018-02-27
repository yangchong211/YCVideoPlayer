package org.yczbj.ycvideoplayer.base.mvp1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/5/18
 * 描    述：懒加载Fragment基类
 * 修订历史：
 * ================================================
 */
public abstract class BaseLazyFragment extends BaseFragment {

    /**
     * 懒加载过
     */
    private boolean isLazyLoaded;
    private boolean isPrepared;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //只有Fragment onCreateView好了
        //另外这里调用一次lazyLoad(）
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    /**
     * 调用懒加载
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    @UiThread
    public abstract void onLazyLoad();


}
