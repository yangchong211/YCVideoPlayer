package org.yczbj.ycvideoplayer.ui.person;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.bottomLayout.BottomDialogFragment;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.home.contract.VideoPlayerMeContract;
import org.yczbj.ycvideoplayer.ui.home.model.DialogListBean;
import org.yczbj.ycvideoplayer.ui.home.model.VideoPlayerComment;
import org.yczbj.ycvideoplayer.ui.home.model.VideoPlayerFavorite;
import org.yczbj.ycvideoplayer.ui.home.presenter.VideoPlayerMePresenter;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.DialogListAdapter;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.NarrowImageAdapter;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.VideoPlayerMeAdapter;
import org.yczbj.ycvideoplayer.util.AppUtil;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.utils.VideoPlayerUtils;
import org.yczbj.ycvideoplayerlib.inter.listener.OnPlayOrPauseListener;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoControlListener;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2018/1/9
 * 描    述：视频播放器详情页面，仿优酷视频播放，使用自己的封装库
 * 修订历史：
 * ================================================
 */
public class VideoPlayerMeActivity extends BaseActivity implements VideoPlayerMeContract.View{

    @BindView(R.id.video_player)
    VideoPlayer videoPlayer;
    @BindView(R.id.recyclerView)
    YCRefreshView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private VideoPlayerMeContract.Presenter presenter = new VideoPlayerMePresenter(this);
    private VideoPlayerMeAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.bindView(this);
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
        return R.layout.activity_video_player_me_detail;
    }


    @Override
    public void initView() {
        StateAppBar.setStatusBarLightMode(this, Color.WHITE);
        initVideoPlayer();
        initYCRefreshView();
    }


    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }


    @Override
    public void initData() {
        recyclerView.showProgress();
        presenter.getData();
    }


    private void initVideoPlayer() {
        //设置播放类型
        //videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
        videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
        //网络视频地址
        String videoUrl = ConstantVideo.VideoPlayerList[7];
        //设置视频地址和请求头部
        videoPlayer.setUp(videoUrl, null);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);

        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("高仿优酷视频播放页面");
        controller.setLoadingType(ConstantKeys.Loading.LOADING_QQ);
        controller.setTopVisibility(true);
        controller.imageView().setBackgroundResource(R.color.blackText);
        controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }
        });
        controller.setOnPlayOrPauseListener(new OnPlayOrPauseListener() {
            @Override
            public void onPlayOrPauseClick(boolean isPlaying) {

            }
        });
        controller.setOnVideoControlListener(new OnVideoControlListener() {
            @Override
            public void onVideoControlClick(int type) {
                switch (type){
                    case ConstantKeys.VideoControl.DOWNLOAD:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"下载音视频");
                        break;
                    case ConstantKeys.VideoControl.AUDIO:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"切换音频");
                        break;
                    case ConstantKeys.VideoControl.SHARE:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"分享内容");
                        break;
                    default:
                        break;
                }
            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new VideoPlayerMeAdapter(this);
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
    }


    /**
     * 添加头部
     */
    private void addHeader() {
        adapter.removeAllHeader();
        initTopHeaderView();
        initHeaderTitle();
        initHorizontalView();
        initVideoContentView();
        initBottomHeaderView();
    }


    @Override
    public void setAdapterView(List<VideoPlayerComment> comments) {
        adapter.addAll(comments);
        adapter.notifyDataSetChanged();
        recyclerView.showRecycler();
    }


    private void initTopHeaderView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(VideoPlayerMeActivity.this).inflate(
                        R.layout.head_video_player_top, parent, false);
            }


            @Override
            public void onBindView(View headerView) {
                TextView tvPlayerTitle = (TextView) headerView.findViewById(R.id.tv_player_title);
                TextView tvPlayerName = (TextView) headerView.findViewById(R.id.tv_player_name);
                TextView tvPlayerDuty = (TextView) headerView.findViewById(R.id.tv_player_duty);
                ImageView ivPlayerCollection = (ImageView) headerView.findViewById(R.id.iv_player_collection);
                ImageView ivPlayerDownload = (ImageView) headerView.findViewById(R.id.iv_player_download);
                ImageView ivPlayerShare = (ImageView) headerView.findViewById(R.id.iv_player_share);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.iv_player_collection:
                                ToastUtil.showToast(VideoPlayerMeActivity.this,"收藏视频");
                                break;
                            case R.id.iv_player_download:
                                ToastUtil.showToast(VideoPlayerMeActivity.this,"下载视频");
                                showDownloadDialog();
                                presenter.startDownload();
                                break;
                            case R.id.iv_player_share:
                                ToastUtil.showToast(VideoPlayerMeActivity.this,"分享视频");
                                break;
                            default:
                                break;
                        }
                    }
                };
                ivPlayerCollection.setOnClickListener(listener);
                ivPlayerDownload.setOnClickListener(listener);
                ivPlayerShare.setOnClickListener(listener);
            }
        });
    }


    private void initHeaderTitle() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(VideoPlayerMeActivity.this).inflate
                        (R.layout.head_video_player_title, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                TextView tvPlayerTitle = (TextView) headerView.findViewById(R.id.tv_player_title);
                tvPlayerTitle.setText("热门推荐");
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
                                ToastUtil.showToast(VideoPlayerMeActivity.this,"没有更多呢！");
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



    private void initVideoContentView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(VideoPlayerMeActivity.this).inflate
                        (R.layout.head_video_player_content, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                TextView tvPlayerCurriculum = (TextView) headerView.findViewById(R.id.tv_player_curriculum);
            }
        });
    }


    private void initBottomHeaderView() {
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(VideoPlayerMeActivity.this);
                tv.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(36)));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                tv.setText("哥们，已经没有数据了……");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    /**
     * 弹出下载对话框
     */
    private void showDownloadDialog() {
        final List<DialogListBean> list = new ArrayList<>();
        for(int a = 0; a< ConstantVideo.VideoPlayerList.length; a++){
            DialogListBean dialogListBean = new DialogListBean("logo",
                    "name","title",ConstantVideo.VideoPlayerList[a]);
            list.add(dialogListBean);
        }
        final BottomDialogFragment dialog = new BottomDialogFragment();
        dialog.setFragmentManager(getSupportFragmentManager());
        dialog.setViewListener(new BottomDialogFragment.ViewListener() {
            @Override
            public void bindView(View v) {
                RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                ImageView ivDownload = (ImageView) v.findViewById(R.id.iv_download);

                recyclerView.setLayoutManager(new LinearLayoutManager(VideoPlayerMeActivity.this));
                DialogListAdapter mAdapter = new DialogListAdapter(VideoPlayerMeActivity.this, list);
                recyclerView.setAdapter(mAdapter);
                final RecycleViewItemLine line = new RecycleViewItemLine(
                        VideoPlayerMeActivity.this, LinearLayout.HORIZONTAL,
                        SizeUtils.dp2px(1),
                        VideoPlayerMeActivity.this.getResources().getColor(R.color.grayLine));
                recyclerView.addItemDecoration(line);
                mAdapter.setOnItemClickListener(new DialogListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            case R.id.iv_cancel:
                                if(VideoPlayerUtils.isActivityLiving(VideoPlayerMeActivity.this)){
                                    dialog.dismissDialogFragment();
                                }
                                break;
                            case R.id.iv_download:

                                break;
                            default:
                                break;
                        }
                    }
                };
                ivCancel.setOnClickListener(listener);
                ivDownload.setOnClickListener(listener);
            }
        });
        dialog.setLayoutRes(R.layout.dialog_bottom_list_view);
        dialog.setDimAmount(0.5f);
        dialog.setTag("BottomDialog");
        dialog.setCancelOutside(true);
        //这个高度可以自己设置，十分灵活
        dialog.setHeight(ScreenUtils.getScreenHeight()-videoPlayer.getHeight()
                -AppUtil.getStatusBarHeight(this));
        dialog.show();
    }


}
