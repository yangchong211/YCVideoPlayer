package org.yczbj.ycvideoplayer.ui.video.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.http.video.VideoModel;
import org.yczbj.ycvideoplayer.base.mvp2.BaseMVPActivity;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleDataBean;
import org.yczbj.ycvideoplayer.ui.video.model.bean.VideoContentBean;
import org.yczbj.ycvideoplayer.ui.video.view.adapter.VideoArticleAdapter;
import org.yczbj.ycvideoplayerlib.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;

import java.util.Random;
import java.util.zip.CRC32;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yc on 2018/2/26.
 *
 *
 */

public class VideoContentActivity extends BaseMVPActivity {

    @Bind(R.id.recyclerView)
    YCRefreshView recyclerView;

    public static final String TAG = "VideoContentActivity";
    private MultiNewsArticleDataBean dataBean;
    private String groupId;
    private String itemId;
    private String videoId;
    private String videoTitle;
    private String shareUrl;
    private VideoArticleAdapter adapter;
    private String image;
    private VideoPlayer videoPlayer;

    public static void launch(MultiNewsArticleDataBean bean) {
        Intent intent = new Intent(Utils.getContext(), VideoContentActivity.class);
        Utils.getContext().startActivity(intent
                .putExtra(VideoContentActivity.TAG, bean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_easy_recycle);
        ButterKnife.bind(this);
        YCAppBar.setStatusBarLightMode(this, Color.WHITE);
        initView();
        initData();
        onLoadData();
    }


    private void initView() {
        initRecyclerView();
    }


    private void initData() {
        Intent intent = getIntent();
        try {
            dataBean = intent.getParcelableExtra(TAG);
            if (null != dataBean.getVideo_detail_info()) {
                if (null != dataBean.getVideo_detail_info().getDetail_video_large_image()) {
                    image = dataBean.getVideo_detail_info().getDetail_video_large_image().getUrl();
                }
            }
            this.groupId = dataBean.getGroup_id() + "";
            this.itemId = dataBean.getItem_id() + "";
            this.videoId = dataBean.getVideo_id();
            this.videoTitle = dataBean.getTitle();
            this.shareUrl = dataBean.getDisplay_url();
        } catch (NullPointerException e) {

        }
        String url = getVideoContentApi(videoId);
        VideoModel model = VideoModel.getInstance();
        getVideoData(model,url);
    }


    private void onLoadData() {

    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new VideoArticleAdapter(this);
        recyclerView.setAdapter(adapter);
        addHeader();
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout swipeToRefresh = recyclerView.getSwipeToRefresh();
                if (swipeToRefresh.isRefreshing()) {
                    recyclerView.setRefreshing(false);
                }
            }
        });
    }


    private void addHeader() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(VideoContentActivity.this).inflate
                        (R.layout.head_video_player, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                videoPlayer = headerView.findViewById(R.id.video_player);
            }
        });
    }

    private void setVideoPlayer(String urls) {
        if(videoPlayer==null){
            return;
        }
        //设置播放类型
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //设置视频地址和请求头部
        videoPlayer.setUp(urls, null);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle(videoTitle);
        controller.setLoadingType(2);
        controller.imageView().setBackgroundResource(R.color.blackText);
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

    private static String getVideoContentApi(String videoid) {
        String VIDEO_HOST = "http://ib.365yg.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        // 将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()} 进行crc32加密
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }

    private static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }


    private void getVideoData(VideoModel model, String url) {
        model.getVideoContent(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<VideoContentBean, String>() {
                    @Override
                    public String apply(@NonNull VideoContentBean videoContentBean) throws Exception {
                        VideoContentBean.DataBean.VideoListBean videoList = videoContentBean.getData().getVideo_list();
                        if (videoList.getVideo_3() != null) {
                            String base64 = videoList.getVideo_3().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            Log.d(TAG, "getVideoUrls: " + url);
                            return url;
                        }

                        if (videoList.getVideo_2() != null) {
                            String base64 = videoList.getVideo_2().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            Log.d(TAG, "getVideoUrls: " + url);
                            return url;
                        }

                        if (videoList.getVideo_1() != null) {
                            String base64 = videoList.getVideo_1().getMain_url();
                            String url = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            Log.d(TAG, "getVideoUrls: " + url);
                            return url;
                        }
                        return null;
                    }
                })
                //.compose(view.<String>bindToLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        setVideoPlayer(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }


}
