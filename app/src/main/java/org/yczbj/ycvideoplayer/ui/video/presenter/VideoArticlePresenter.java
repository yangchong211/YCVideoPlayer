package org.yczbj.ycvideoplayer.ui.video.presenter;

import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;


import org.yczbj.ycvideoplayer.api.http.video.VideoModel;
import org.yczbj.ycvideoplayer.ui.video.contract.IVideoArticle;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleBean;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleDataBean;
import org.yczbj.ycvideoplayer.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class VideoArticlePresenter implements IVideoArticle.Presenter {

    private static final String TAG = "VideoArticlePresenter";
    private IVideoArticle.View view;
    private String category;
    private String time;
    private Gson gson = new Gson();
    private List<MultiNewsArticleDataBean> dataList = new ArrayList<>();

    public VideoArticlePresenter(IVideoArticle.View view) {
        this.view = view;
        this.time = TimeUtils.getNowString();
    }

    @Override
    public void doLoadData(String... category) {
        try {
            if (null == this.category) {
                this.category = category[0];
            }
        } catch (Exception e) {
            LogUtils.e(e.getLocalizedMessage());
        }

        // 释放内存
        if (dataList.size() > 100) {
            dataList.clear();
        }

        VideoModel model = VideoModel.getInstance();
        model.getVideoArticle(this.category, time)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .switchMap(new Function<MultiNewsArticleBean, Observable<MultiNewsArticleDataBean>>() {
                    @Override
                    public Observable<MultiNewsArticleDataBean> apply(@NonNull MultiNewsArticleBean multiNewsArticleBean) throws Exception {
                        List<MultiNewsArticleDataBean> dataList = new ArrayList<>();
                        for (MultiNewsArticleBean.DataBean dataBean : multiNewsArticleBean.getData()) {
                            dataList.add(gson.fromJson(dataBean.getContent(), MultiNewsArticleDataBean.class));
                        }
                        return Observable.fromIterable(dataList);
                    }
                })
                .filter(new Predicate<MultiNewsArticleDataBean>() {
                    @Override
                    public boolean test(@NonNull MultiNewsArticleDataBean dataBean) throws Exception {
                        time = dataBean.getBehot_time();
                        if (TextUtils.isEmpty(dataBean.getSource())) {
                            return false;
                        }
                        try {
                            // 过滤头条问答新闻
                            if (dataBean.getSource().contains("头条问答")
                                    || dataBean.getTag().contains("ad")
                                    || dataBean.getSource().contains("话题")) {
                                return false;
                            }
                        } catch (NullPointerException e) {

                        }
                        // 过滤重复新闻(与上次刷新的数据比较)
                        for (MultiNewsArticleDataBean bean : dataList) {
                            if (bean.getTitle().equals(dataBean.getTitle())) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .toList()
                .compose(view.<List<MultiNewsArticleDataBean>>bindToLife())
                .subscribe(new Consumer<List<MultiNewsArticleDataBean>>() {
                    @Override
                    public void accept(@NonNull List<MultiNewsArticleDataBean> list) throws Exception {
                        doSetAdapter(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        doShowNetError();
                        LogUtils.e(throwable.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData();
    }

    @Override
    public void doSetAdapter(List<MultiNewsArticleDataBean> dataBeen) {
        dataList.addAll(dataBeen);
        view.onSetAdapter(dataList);
        view.onHideLoading();
    }

    @Override
    public void doRefresh() {
        if (dataList.size() != 0) {
            dataList.clear();
            time = TimeUtils.getNowString();
        }
        doLoadData();
    }


    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

}
