package org.yczbj.ycvideoplayer.ui.movie.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.home.model.VideoPlayerFavorite;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.NarrowImageAdapter;
import org.yczbj.ycvideoplayer.ui.movie.contract.MovieDetailContract;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieDetailBean;
import org.yczbj.ycvideoplayer.ui.movie.presenter.MovieDetailPresenter;
import org.yczbj.ycvideoplayer.ui.movie.view.adapter.MovieDetailAdapter;
import org.yczbj.ycvideoplayer.util.ImageUtil;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by yc on 2018/3/2
 *
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View{


    @BindView(R.id.video_player)
    VideoPlayer videoPlayer;
    @BindView(R.id.recyclerView)
    YCRefreshView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private MovieDetailAdapter adapter;

    private MovieDetailContract.Presenter presenter = new MovieDetailPresenter(this);
    private String dataId;
    private VideoPlayerController controller;

    private TextView tvPlayerCurriculum;
    private View mContentView;
    private TextView tvName;
    private TextView tvType;
    private TextView tvAuthor;
    private TextView tvTime;
    private TextView tvCity;
    private ImageView ivImageAd;
    private TextView tvTitleAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.subscribe();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }


    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }


    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.base_video_view;
    }


    @Override
    public void initView() {
        fab.setVisibility(View.VISIBLE);
        initIntentData();
        initVideoPlayer();
        initYCRefreshView();
    }


    private void initIntentData() {
        Intent intent = getIntent();
        dataId = intent.getStringExtra("dataId");
    }


    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(adapter.getAllData()!=null && adapter.getAllData().size()>0){
                    Intent intent = new Intent(MovieDetailActivity.this, MovieDetailActivity.class);
                    intent.putExtra("dataId",adapter.getAllData().get(position).getDataId());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void initData() {
        recyclerView.showProgress();
        presenter.getData(dataId);
    }


    private void initVideoPlayer() {
        //设置播放类型
        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
        //网络视频地址
        //设置视频地址和请求头部
        //创建视频控制器
        controller = new VideoPlayerController(this);
        controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new MovieDetailAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshing(false);
        addHeader();
    }


    private void addHeader() {
        if(adapter.getHeaderCount()>0){
            adapter.removeAllHeader();
        }
        initVideoContentView();
        initHorizontalView();
        initHeaderTitle();
    }


    private void initVideoContentView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                mContentView = LayoutInflater.from(MovieDetailActivity.this).inflate
                        (R.layout.head_video_player_content, parent, false);
                return mContentView;
            }

            @Override
            public void onBindView(View headerView) {
                tvPlayerCurriculum = headerView.findViewById(R.id.tv_player_curriculum);
                tvName = headerView.findViewById(R.id.tv_name);
                tvType = headerView.findViewById(R.id.tv_type);
                tvAuthor = headerView.findViewById(R.id.tv_author);
                tvTime = headerView.findViewById(R.id.tv_time);
                tvCity = headerView.findViewById(R.id.tv_city);
                ivImageAd = headerView.findViewById(R.id.iv_image_ad);
                tvTitleAd = headerView.findViewById(R.id.tv_title_ad);
            }
        });
    }



    private void initHorizontalView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(SizeUtils.dp2px(10),SizeUtils.dp2px(5),
                        SizeUtils.dp2px(10),SizeUtils.dp2px(5));
                recyclerView.setLayoutParams(layoutParams);
                final NarrowImageAdapter narrowAdapter;
                recyclerView.setAdapter(narrowAdapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL,false));
                recyclerView.addItemDecoration(new SpaceViewItemLine(SizeUtils.dp2px(8)));

                narrowAdapter.setMore(R.layout.view_video_more_horizontal, new RecyclerArrayAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(MovieDetailActivity.this,"没有更多呢！");
                            }
                        },1000);
                    }
                });
                List<VideoPlayerFavorite> favoriteList = new ArrayList<>();
                for(int a=0 ; a<10 ; a++){
                    VideoPlayerFavorite videoPlayerFavorite = new VideoPlayerFavorite(
                            "这个是猜你喜欢的标题",R.drawable.bg_small_tree_min,"");
                    favoriteList.add(videoPlayerFavorite);
                }
                narrowAdapter.addAll(favoriteList);
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    private void initHeaderTitle() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(MovieDetailActivity.this).inflate
                        (R.layout.head_video_player_title, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                TextView tvPlayerTitle = headerView.findViewById(R.id.tv_player_title);
                tvPlayerTitle.setText("热门推荐");
            }
        });
    }



    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void setAdapterData(MovieDetailBean movieDetailBean) {
        if(movieDetailBean!=null && movieDetailBean.getRet()!=null){
            MovieDetailBean.RetBean ret = movieDetailBean.getRet();
            String hdUrl = ret.getHDURL();
            videoPlayer.setUp(hdUrl,null);
            controller.setTitle(ret.getTitle());
            String duration = ret.getDuration();
            controller.setLength(duration);
            controller.setLoadingType(ConstantKeys.Loading.LOADING_QQ);

            if(mContentView!=null){
                tvPlayerCurriculum.setText(ret.getDescription());
                tvName.setText("电影名称："+ret.getTitle());
                tvAuthor.setText("人物主演："+ret.getActors());
                tvType.setText("电影类型："+ret.getVideoType());
                tvTime.setText("放映时间："+ret.getAirTime());
                tvCity.setText("上映国家："+ret.getRegion());

                if(ret.getAdv()!=null){
                    ImageUtil.loadImgByPicasso(MovieDetailActivity.this,
                            ret.getAdv().getImgURL(),R.drawable.bg_small_autumn_tree_min,ivImageAd);
                    tvTitleAd.setText(ret.getAdv().getTitle());
                }else {
                    ImageUtil.loadImgByPicasso(MovieDetailActivity.this,R.drawable.bg_small_autumn_tree_min,ivImageAd);
                    tvTitleAd.setText("潇湘剑雨");
                }
            }


            ImageUtil.loadImgByPicasso(this,ret.getPic(),controller.imageView());

            List<MovieDetailBean.RetBean.ListBean.ChildListBean> mLists = new ArrayList<>();
            List<MovieDetailBean.RetBean.ListBean> list = ret.getList();
            if(list!=null && list.size()>0){
                for(int a=0 ; a<list.size() ; a++){
                    switch (list.get(a).getTitle()){
                        case "猜你喜欢":
                            mLists.addAll(list.get(a).getChildList());
                            break;
                        case "":
                            mLists.addAll(list.get(a).getChildList());
                            break;
                        default:
                            break;
                    }
                }
            }
            if(adapter==null){
                adapter = new MovieDetailAdapter(this);
            }else {
                adapter.clear();
            }
            adapter.addAll(mLists);
            adapter.notifyDataSetChanged();
            recyclerView.showRecycler();
        }
    }

    @Override
    public void setError() {
        recyclerView.showError();
    }

    @Override
    public void setEmptyView() {
        recyclerView.showEmpty();
    }


}
