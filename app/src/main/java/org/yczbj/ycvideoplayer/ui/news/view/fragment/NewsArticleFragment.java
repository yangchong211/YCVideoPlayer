package org.yczbj.ycvideoplayer.ui.news.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import org.yczbj.ycvideoplayer.base.mvp2.BaseList1Fragment;
import org.yczbj.ycvideoplayer.listener.OnLoadMoreListener;
import org.yczbj.ycvideoplayer.model.LoadingBean;
import org.yczbj.ycvideoplayer.ui.news.contract.INewsArticle;
import org.yczbj.ycvideoplayer.ui.news.presenter.NewsArticlePresenter;
import org.yczbj.ycvideoplayer.util.DiffCallback;
import org.yczbj.ycvideoplayer.util.Register;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class NewsArticleFragment extends BaseList1Fragment<INewsArticle.Presenter> implements INewsArticle.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewsArticleFragment";
    private String categoryId;

    public static NewsArticleFragment newInstance(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, categoryId);
        NewsArticleFragment newsArticleView = new NewsArticleFragment();
        newsArticleView.setArguments(bundle);
        return newsArticleView;
    }

    @Override
    protected void initData() {
        categoryId = getArguments().getString(TAG);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        adapter = new MultiTypeAdapter(oldItems);
        Register.registerNewsArticleItem(adapter);
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

    /**
     * 懒加载调用请求数据接口
     */
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
     * 设置presenter
     * @param presenter         presenter
     */
    @Override
    public void setPresenter(INewsArticle.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new NewsArticlePresenter(this);
        }
    }

}
