package org.yczbj.ycvideoplayer.ui.movie.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.first.BannerView;
import com.yc.cn.ycbannerlib.first.util.SizeUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;
import org.yczbj.ycvideoplayer.ui.movie.contract.MovieContract;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.ui.movie.presenter.MoviePresenter;
import org.yczbj.ycvideoplayer.ui.movie.view.activity.MovieNewsActivity;
import org.yczbj.ycvideoplayer.ui.movie.view.adapter.MovieAdapter;
import org.yczbj.ycvideoplayer.ui.movie.view.adapter.MovieBannerAdapter;
import org.yczbj.ycvideoplayer.ui.movie.view.activity.MovieDetailActivity;
import org.yczbj.ycvideoplayer.ui.test.test4.MediaPlayerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by yc on 2018/3/1.
 *
 */
public class MovieFragment extends BaseFragment implements MovieContract.View{

    @BindView(R.id.recyclerView)
    YCRefreshView recyclerView;
    private MainActivity activity;
    private MovieAdapter adapter;
    private BannerView mBanner;

    private MovieContract.Presenter presenter = new MoviePresenter(this);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mBanner!=null){
            mBanner.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBanner!=null){
            mBanner.resume();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.subscribe();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initView() {
        initYCRefreshView();
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(adapter.getAllData()!=null && adapter.getAllData().size()>0){
                    Intent intent = new Intent(activity, MovieDetailActivity.class);
                    intent.putExtra("dataId",adapter.getAllData().get(position).getDataId());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void initData() {
        recyclerView.showProgress();
        presenter.getData();
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new MovieAdapter(activity);
        recyclerView.setAdapter(adapter);
        addHeader();
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout swipeToRefresh = recyclerView.getSwipeToRefresh();
                if(swipeToRefresh.isRefreshing()){
                    recyclerView.setRefreshing(false);
                }
            }
        });
        //加载更多
        adapter.setMore(R.layout.view_recycle_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                if (NetworkUtils.isConnected()) {

                } else {
                    adapter.pauseMore();
                    ToastUtil.showToast(activity,"网络不可用");
                }
            }

            @Override
            public void onMoreClick() {

            }
        });

        //设置没有数据
        adapter.setNoMore(R.layout.view_recycle_no_more, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore();
                } else {
                    ToastUtil.showToast(activity,"网络不可用");
                }
            }

            @Override
            public void onNoMoreClick() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore();
                } else {
                    ToastUtil.showToast(activity,"网络不可用");
                }
            }
        });

        //设置错误
        adapter.setError(R.layout.view_recycle_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
    }


    /**
     * 添加头部
     */
    private void addHeader() {
        if(adapter.getHeaderCount()!=0){
            adapter.removeAllHeader();
        }
        initTopHeaderView();
        initContentView();
    }


    private void initTopHeaderView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_video_banner, parent, false);
            }


            @Override
            public void onBindView(View headerView) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.tv_special_first:
                                startActivity(MediaPlayerActivity.class);
                                break;
                            case R.id.tv_special_second:
                                startActivity(MovieNewsActivity.class);
                                break;
                            case R.id.tv_special_third:

                                break;
                            case R.id.tv_special_four:

                                break;
                            case R.id.tv_special_five:

                                break;
                            default:
                                break;
                        }
                    }
                };
                headerView.findViewById(R.id.tv_special_first).setOnClickListener(listener);
                headerView.findViewById(R.id.tv_special_second).setOnClickListener(listener);
                headerView.findViewById(R.id.tv_special_third).setOnClickListener(listener);
                headerView.findViewById(R.id.tv_special_four).setOnClickListener(listener);
                headerView.findViewById(R.id.tv_special_five).setOnClickListener(listener);

                // 绑定数据
                mBanner = headerView.findViewById(R.id.banner);
                mBanner.setHintGravity(5);
                mBanner.setAnimationDuration(1000);
                mBanner.setPlayDelay(2000);
                mBanner.setHintPadding(0,0, SizeUtil.dip2px(activity,10), SizeUtil.dip2px(activity,10));
            }
        });
    }


    private void initContentView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_video_content, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    @Override
    public void setAdapterData(MovieBean movieBean) {
        final List<MovieBean.RetBean.ListBean.ChildListBean> bannerList = new ArrayList<>();
        List<MovieBean.RetBean.ListBean.ChildListBean> movieList = new ArrayList<>();
        List<MovieBean.RetBean.ListBean> list = movieBean.getRet().getList();
        List<MovieBean.RetBean.HotSearchListBean> hotSearchList = movieBean.getRet().getHotSearchList();
        for (int a=0 ; a<list.size() ; a++){
            switch (list.get(a).getTitle()){
                case "Banner":
                    bannerList.addAll(list.get(a).getChildList());
                    break;
                case "免费推荐":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "热点资讯":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "精彩推荐":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "大咖剧场":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "电影资讯":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "大片抢先看":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "微电影":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "香港映象":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                case "好莱坞":
                    movieList.addAll(list.get(a).getChildList());
                    break;
                default:
                    break;
            }
        }
        if(bannerList.size()>0){
            if(mBanner!=null){
                mBanner.setAdapter(new MovieBannerAdapter(activity, bannerList));
                mBanner.setOnBannerClickListener(new BannerView.OnBannerClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if(bannerList.size()>0){
                            Intent intent = new Intent(activity, MovieDetailActivity.class);
                            intent.putExtra("dataId",bannerList.get(position).getDataId());
                            startActivity(intent);
                        }
                    }
                });
            }
        }
        if(movieList.size()>0){
            adapter.clear();
            adapter.addAll(movieList);
            adapter.notifyDataSetChanged();
            recyclerView.showRecycler();
        }else{
            recyclerView.showEmpty();
            recyclerView.setEmptyView(R.layout.view_custom_empty_data);
        }
    }


}
