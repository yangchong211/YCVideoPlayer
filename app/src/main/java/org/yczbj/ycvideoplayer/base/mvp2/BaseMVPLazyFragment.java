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

    /**
     * Fragment的View加载完毕的标记
     */
    protected boolean isViewInitiated;
    /**
     * Fragment对用户可见的标记
     */
    protected boolean isVisibleToUser;
    /**
     * 数据是否初始化
     */
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 第一步,改变isViewInitiated标记
     * 当onViewCreated()方法执行时,表明View已经加载完毕,此时改变isViewInitiated标记为true,并调用lazyLoad()方法
     */
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
     * 第二步
     * 此方法会在onCreateView(）之前执行
     * 当viewPager中fragment改变可见状态时也会调用
     * 当fragment 从可见到不见，或者从不可见切换到可见，都会调用此方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    /**
     * 第四步:定义抽象方法fetchData(),具体加载数据的工作,交给子类去完成
     */
    public abstract void fetchData();

    /**
     * 第三步:在lazyLoad()方法中进行双重标记判断,通过后即可进行数据加载
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

    /**
     * 第三步:在lazyLoad()方法中进行双重标记判断,通过后即可进行数据加载
     * 用户强制刷新的话，就应该是用户主动进行刷新了，当然也要去取数据了，用户第一嘛
     */
    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

}