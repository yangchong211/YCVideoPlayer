package org.yczbj.ycvideoplayer.base.mvp2;

import android.os.Bundle;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2018/2/24
 * 描    述：预加载
 * 修订历史：
 * ================================================
 */
public abstract class BaseMVPLazyFragment<T extends IBasePresenter> extends BaseMVPFragment<T> {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        //只有Fragment onCreateView好了，
        //另外这里调用一次lazyLoad(）
        prepareFetchData();
        //lazyLoad();
    }

    /**
     * 此方法会在onCreateView(）之前执行
     * 当viewPager中fragment改变可见状态时也会调用
     * 当fragment 从可见到不见，或者从不可见切换到可见，都会调用此方法
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    /**
     * 第一种方法
     * 调用懒加载，getUserVisibleHint()会返回是否可见状态
     * 这是fragment实现懒加载的关键,只有fragment 可见才会调用onLazyLoad() 加载数据
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && isViewInitiated && !isDataInitiated) {
            fetchData();
            isDataInitiated = true;
        }
    }


    /**
     * 第二种方法
     * 调用懒加载
     */
    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }


    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

}