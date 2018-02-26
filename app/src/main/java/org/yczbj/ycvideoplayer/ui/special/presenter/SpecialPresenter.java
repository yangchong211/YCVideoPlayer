package org.yczbj.ycvideoplayer.ui.special.presenter;

import android.app.Activity;

import org.yczbj.ycvideoplayer.ui.home.contract.HomeContract;
import org.yczbj.ycvideoplayer.ui.special.contract.SpecialContract;
import org.yczbj.ycvideoplayer.ui.special.model.SpecialBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class SpecialPresenter implements SpecialContract.Presenter {

    private SpecialContract.View mView;
    private CompositeDisposable mSubscriptions;
    private Activity activity;

    public SpecialPresenter(SpecialContract.View androidView) {
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
    public void getData() {
        List<SpecialBean> list = new ArrayList<>();
        for(int a=0 ; a<10 ; a++){
            SpecialBean bean = new SpecialBean("title","content","time","author",0);
            list.add(bean);
        }
        mView.setAdapterView(list);
    }
}
