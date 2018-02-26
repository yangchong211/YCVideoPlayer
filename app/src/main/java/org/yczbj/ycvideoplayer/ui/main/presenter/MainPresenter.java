package org.yczbj.ycvideoplayer.ui.main.presenter;

import android.app.Activity;
import android.content.res.TypedArray;

import com.flyco.tablayout.listener.CustomTabEntity;


import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.model.bean.TabEntity;
import org.yczbj.ycvideoplayer.ui.main.contract.MainContract;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/3/18
 * 描    述：Main主页面
 * 修订历史：
 * ================================================
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private CompositeDisposable mSubscriptions;
    private Activity activity;

    public MainPresenter(MainContract.View androidView) {
        this.mView = androidView;
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        if(activity!=null){
            activity = null;
        }
    }

    @Override
    public void bindView(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public ArrayList<CustomTabEntity> getTabEntity() {
        if(activity==null){
            activity = mView.getActivity();
        }
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        TypedArray mIconUnSelectIds = activity.getResources().obtainTypedArray(R.array.main_tab_un_select);
        TypedArray mIconSelectIds = activity.getResources().obtainTypedArray(R.array.main_tab_select);
        String[] mainTitles = activity.getResources().getStringArray(R.array.main_title);
        for (int i = 0; i < mainTitles.length; i++) {
            int unSelectId = mIconUnSelectIds.getResourceId(i, R.drawable.tab_home_unselect);
            int selectId = mIconSelectIds.getResourceId(i, R.drawable.tab_home_select);
            mTabEntities.add(new TabEntity(mainTitles[i],selectId , unSelectId));
        }
        mIconUnSelectIds.recycle();
        mIconSelectIds.recycle();
        return mTabEntities;
    }


}
