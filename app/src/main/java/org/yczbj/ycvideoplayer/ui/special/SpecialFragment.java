package org.yczbj.ycvideoplayer.ui.special;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.first.BannerView;
import com.yc.cn.ycbannerlib.first.util.SizeUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;
import org.yczbj.ycvideoplayer.ui.home.model.VideoPlayerFavorite;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.BannerPagerAdapter;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.NarrowImageAdapter;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;
import org.yczbj.ycvideoplayer.ui.special.contract.SpecialContract;
import org.yczbj.ycvideoplayer.ui.special.model.SpecialBean;
import org.yczbj.ycvideoplayer.ui.special.presenter.SpecialPresenter;
import org.yczbj.ycvideoplayer.ui.special.view.SpecialAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class SpecialFragment extends BaseFragment implements SpecialContract.View {


    @BindView(R.id.recyclerView)
    YCRefreshView recyclerView;
    private MainActivity activity;

    private SpecialContract.Presenter presenter = new SpecialPresenter(this);
    private SpecialAdapter adapter;
    private BannerView mBanner;

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
        return R.layout.base_easy_recycle;
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
        adapter = new SpecialAdapter(activity);
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
        adapter.removeAllHeader();
        initTopHeaderView();
        initVideoContentView();
        initHeaderTitle();
        initHorizontalView();
        initBottomHeaderView();
    }


    private void initTopHeaderView() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_special_top_view,
                        parent, false);
            }


            @Override
            public void onBindView(View headerView) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()){
                            //启动单任务下载
                            case R.id.tv_special_first:
                                break;
                            //多任务下载
                            case R.id.tv_special_second:
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
                mBanner = (BannerView) headerView.findViewById(R.id.banner);
                mBanner.setHintGravity(2);
                mBanner.setAnimationDuration(1000);
                mBanner.setPlayDelay(3000);
                mBanner.setHintPadding(0,0,0, SizeUtil.dip2px(activity,10));
                mBanner.setAdapter(new BannerPagerAdapter(activity, arrayList));
            }
        });
    }

    private void initHeaderTitle() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate
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
                RecyclerView recyclerView = new RecyclerView(parent.getContext()) {
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
                layoutParams.setMargins(SizeUtils.dp2px(10), SizeUtils.dp2px(5),
                        SizeUtils.dp2px(10), SizeUtils.dp2px(5));
                recyclerView.setLayoutParams(layoutParams);
                final NarrowImageAdapter narrowAdapter;
                recyclerView.setAdapter(narrowAdapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.addItemDecoration(new SpaceViewItemLine(SizeUtils.dp2px(8)));

                narrowAdapter.setMore(R.layout.view_video_more_horizontal, new RecyclerArrayAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(activity, "没有更多呢！");
                            }
                        }, 1000);
                    }
                });
                List<VideoPlayerFavorite> favoriteList = new ArrayList<>();
                for (int a = 0; a < 10; a++) {
                    VideoPlayerFavorite videoPlayerFavorite = new VideoPlayerFavorite(
                            "这个是猜你喜欢的标题", R.drawable.bg_small_tree_min, "");
                    favoriteList.add(videoPlayerFavorite);

                }
                narrowAdapter.addAll(favoriteList);
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup) headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    private void initVideoContentView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate
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
                TextView tv = new TextView(activity);
                tv.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(36)));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv.setText("哥们，已经没有数据了……");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    @Override
    public void setAdapterView(List<SpecialBean> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    recyclerView.showRecycler();
                }
            }
        });
    }
}
