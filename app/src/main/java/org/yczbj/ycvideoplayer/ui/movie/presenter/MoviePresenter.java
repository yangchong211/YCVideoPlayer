package org.yczbj.ycvideoplayer.ui.movie.presenter;

import android.app.Activity;
import android.text.TextUtils;

import org.reactivestreams.Subscription;
import org.yczbj.ycvideoplayer.api.http.movie.MovieModel;
import org.yczbj.ycvideoplayer.ui.movie.contract.MovieContract;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class MoviePresenter implements MovieContract.Presenter {

    private MovieContract.View mView;
    private CompositeDisposable mSubscriptions;
    private Activity activity;

    public MoviePresenter(MovieContract.View androidView) {
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
        MovieModel model = MovieModel.getInstance();
        Disposable subscribe = model.getHomePage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieBean>() {
                    @Override
                    public void accept(MovieBean movieBean) throws Exception {
                        if(movieBean!=null && movieBean.getRet()!=null){
                            mView.setAdapterData(movieBean);
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }



}
