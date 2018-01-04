package org.yczbj.ycvideoplayer.ui.home.presenter;

import android.app.Activity;
import android.content.res.TypedArray;

import com.flyco.tablayout.listener.CustomTabEntity;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.bean.TabEntity;
import org.yczbj.ycvideoplayer.ui.home.contract.HomeContract;
import org.yczbj.ycvideoplayer.ui.main.contract.MainContract;
import org.yczbj.ycvideoplayer.ui.main.view.MainActivity;

import java.util.ArrayList;

import rx.subscriptions.CompositeSubscription;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/3/18
 * 描    述：Home主页面
 * 修订历史：
 * ================================================
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private CompositeSubscription mSubscriptions;
    private Activity activity;

    public HomePresenter(HomeContract.View androidView) {
        this.mView = androidView;
        mSubscriptions = new CompositeSubscription();
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


}
