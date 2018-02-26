package org.yczbj.ycvideoplayer.ui.video.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;


import org.yczbj.ycvideoplayer.base.mvp2.BaseListFragment;
import org.yczbj.ycvideoplayer.listener.OnLoadMoreListener;
import org.yczbj.ycvideoplayer.model.LoadingBean;
import org.yczbj.ycvideoplayer.ui.video.contract.IVideoArticle;
import org.yczbj.ycvideoplayer.ui.video.presenter.VideoArticlePresenter;
import org.yczbj.ycvideoplayer.util.DiffCallback;
import org.yczbj.ycvideoplayer.util.Register;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class VideoArticleFragment extends BaseListFragment<IVideoArticle.Presenter> implements IVideoArticle.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "VideoArticleView";
    private String categoryId;

    public static VideoArticleFragment newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, categoryId);
        VideoArticleFragment videoArticleView = new VideoArticleFragment();
        videoArticleView.setArguments(bundle);
        return videoArticleView;
    }

    @Override
    protected void initData() {
        categoryId = getArguments().getString(TAG);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerVideoArticleItem(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    presenter.doLoadMoreData();
                }
            }
        });
    }

    @Override
    public void fetchData() {
        super.fetchData();
        onLoadData();
    }

    @Override
    public void onLoadData() {
        onShowLoading();
        presenter.doLoadData(categoryId);
    }

    @Override
    public void onSetAdapter(final List<?> list) {
        Items newItems = new Items(list);
        newItems.add(new LoadingBean());
        DiffCallback.create(oldItems, newItems, adapter);
        oldItems.clear();
        oldItems.addAll(newItems);
        canLoadMore = true;
        recyclerView.stopScroll();
    }

    /**
     * API 跟新闻的一样 所以采用新闻的 presenter
     *
     * @param presenter
     */
    @Override
    public void setPresenter(IVideoArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new VideoArticlePresenter(this);
        }
    }


}
