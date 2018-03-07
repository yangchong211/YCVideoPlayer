package org.yczbj.ycvideoplayer.ui.movie.presenter;

import android.app.Activity;

import org.yczbj.ycvideoplayer.api.http.movie.MovieModel;
import org.yczbj.ycvideoplayer.ui.movie.contract.MovieContract;
import org.yczbj.ycvideoplayer.ui.movie.contract.MovieDetailContract;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieDetailBean;

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

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View mView;
    private CompositeDisposable mSubscriptions;
    private Activity activity;

    public MovieDetailPresenter(MovieDetailContract.View androidView) {
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
    public void getData(String dataId) {
        MovieModel model = MovieModel.getInstance();
        Disposable subscribe = model.getVideoInfo(dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieDetailBean>() {
                    @Override
                    public void accept(MovieDetailBean movieDetailBean) throws Exception {
                        if(movieDetailBean!=null){
                            mView.setAdapterData(movieDetailBean);
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }


}
