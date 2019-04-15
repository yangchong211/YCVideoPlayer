package org.yczbj.ycvideoplayer.ui.home.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import com.yc.cn.ycbannerlib.first.BannerView;
import com.yc.cn.ycbannerlib.first.util.SizeUtil;
import com.yc.cn.ycbaseadapterlib.BaseViewHolder;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.Constant;
import org.yczbj.ycvideoplayer.base.BaseConfig;
import org.yczbj.ycvideoplayer.base.BaseDelegateAdapter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;
import org.yczbj.ycvideoplayer.ui.person.TestFourWindowActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.TestMyActivity;
import org.yczbj.ycvideoplayer.ui.home.view.activity.VideoPlayerJzActivity;
import org.yczbj.ycvideoplayer.ui.person.VideoPlayerMeActivity;
import org.yczbj.ycvideoplayer.ui.home.view.adapter.BannerPagerAdapter;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;
import org.yczbj.ycvideoplayer.util.LogUtils;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/11/18
 * 描    述：home主页面
 * 修订历史：
 * ================================================
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_bind)
    LinearLayout llBind;

    private MainActivity activity;
    private BannerView mBanner;
    private VirtualLayoutManager layoutManager;
    private int lastVisibleItem;
    /**
     * 存放各个模块的适配器
     */
    private List<DelegateAdapter.Adapter> mAdapters;
    private DelegateAdapter delegateAdapter;
    private VideoPlayerController controller;

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
        if (mBanner != null) {
            mBanner.pause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.resume();
        }
        initOldUserBinding();
    }

    /**
     * onHiddenChanged这个方法可以用来在切换Fragment的时候，进行一些即时的操作（如改变后要刷新、保存等）。
     * @param hidden        是否隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            initOldUserBinding();
        }
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        initOldUserBinding();
        initVLayout();
        initRefreshView();
    }


    private void initOldUserBinding() {
        boolean login = BaseConfig.INSTANCE.getIsLogin();
        if (login) {
            llBind.setVisibility(View.GONE);
        } else {
            llBind.setVisibility(View.VISIBLE);
        }
        llBind.setVisibility(View.VISIBLE);
    }


    private void initVLayout() {
        mAdapters = new LinkedList<>();
        //初始化
        //创建VirtualLayoutManager对象
        layoutManager = new VirtualLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        //自定义各种不同适配器
        initAllTypeView();
        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }


    private boolean isTopShow = true;
    private void initRefreshView() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refresh.isRefreshing()) {
                    refresh.setRefreshing(false);
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (refresh.isRefreshing()) {
                    return;
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem == delegateAdapter.getItemCount() -1 ){
                        //加载更多
                        ToastUtil.showToast(activity, "没有更多数据!!!!!");
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                boolean isTopScroll = recyclerView.canScrollVertically(-1);
                //判断是否滑到了顶部
                if(!isTopScroll){
                    isTopShow = true;
                    llBind.animate().translationY(0);
                    return;
                }
                float scaleY = recyclerView.getScaleY();
                LogUtils.e("onScrolled"+scaleY+"---"+dy);
                if(scaleY - dy > 0 && isTopShow) {
                    //下移隐藏
                    isTopShow = false;
                    llBind.animate().translationY(-llBind.getHeight());
                } else if(scaleY - dy < 0 && !isTopShow){
                    //上移出现
                    isTopShow = true;
                    llBind.animate().translationY(0);
                }
                /*if(scaleY-dy>0 && isTopShow){
                    isTopShow = false;
                    Animation translateAnimation = AnimationsUtils
                            .getTranslateAnimation(0,
                                    0, -(float) llBind.getHeight(), 0, 500);
                    llBind.setAnimation(translateAnimation);
                }else if(scaleY-dy<0 && !isTopShow){
                    isTopShow = true;
                    Animation translateAnimation = AnimationsUtils
                            .getTranslateAnimation(0,
                                    0, 0, (float) llBind.getHeight(), 1000);
                    llBind.setAnimation(translateAnimation);
                }*/
            }
        });
    }


    @Override
    public void initListener() {
        llSearch.setOnClickListener(this);
        llBind.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search:

                break;
            case R.id.ll_bind:

                break;
            default:
                break;
        }
    }

    /**
     * 添加不同类型数据布局
     */
    private void initAllTypeView() {
        initBannerView();
        initFiveButtonView();
        initListFirstView();
        initFirstAdView();
        initListSecondView();
        initSecondAdView();
        initListThirdView();
        initListFourView();
        initListFiveView();
        initListSixView();
    }


    private void initBannerView() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        //banner
        //banner
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.view_vlayout_banner, 1, Constant.viewType.typeBanner) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                mBanner = holder.getView(R.id.banner);
                mBanner.setHintGravity(1);
                mBanner.setAnimationDuration(1000);
                mBanner.setPlayDelay(2000);
                mBanner.setHintPadding(0, 0, 0, SizeUtil.dip2px(activity, 10));
                mBanner.setAdapter(new BannerPagerAdapter(activity, arrayList));

            }
        };
        mAdapters.add(adapter);
    }


    private void initFiveButtonView() {
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.view_vlayout_button, 1, Constant.viewType.typeView) {
                @Override
                public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_home_first:
                                startActivity(TestFourWindowActivity.class);
                                break;
                            case R.id.tv_home_second:
                                startActivity(TestMyActivity.class);
                                break;
                            case R.id.tv_home_third:
                                startActivity(VideoPlayerMeActivity.class);
                                break;
                            case R.id.tv_home_four:
                                startActivity(VideoPlayerJzActivity.class);
                                break;
                            case R.id.tv_home_five:

                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.tv_home_first).setOnClickListener(listener);
                holder.getView(R.id.tv_home_second).setOnClickListener(listener);
                holder.getView(R.id.tv_home_third).setOnClickListener(listener);
                holder.getView(R.id.tv_home_four).setOnClickListener(listener);
                holder.getView(R.id.tv_home_five).setOnClickListener(listener);
            }
        };
        mAdapters.add(adapter);
    }


    private void initListFirstView() {
        initTitleView(1);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.view_vlayout_grid, 4, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final ImageView ivImage = holder.getView(R.id.iv_image);
                ivImage.setBackgroundResource(R.drawable.bg_small_tree_min);
                //String image = ConstantImage.homePageConcentration[position];
                //ImageUtil.loadImgByPicasso(activity,ConstantImage.homePageConcentration[position],R.drawable.image_default,ivImage);
            }
        };
        mAdapters.add(adapter);
        initMoreView(1);
    }


    private void initFirstAdView() {
        BaseDelegateAdapter adAdapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.view_vlayout_ad, 1, Constant.viewType.typeAd) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        mAdapters.add(adAdapter);
    }


    private void initListSecondView() {
        initTitleView(2);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.viewType.typeList2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        mAdapters.add(adapter);
        initMoreView(2);
    }


    private void initSecondAdView() {
        BaseDelegateAdapter adAdapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.view_vlayout_ad, 1, Constant.viewType.typeAd2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.iv_image_ad).setBackgroundResource(R.drawable.bg_small_leaves_min);

            }
        };
        mAdapters.add(adAdapter);
    }


    private void initListThirdView() {
        initTitleView(3);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity,
                gridLayoutHelper, R.layout.view_vlayout_grid, 2, Constant.viewType.typeGv3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView ivImage = holder.getView(R.id.iv_image);
                ivImage.setImageResource(R.drawable.bg_small_autumn_tree_min);
            }
        };
        mAdapters.add(adapter);
        initMoreView(3);
    }


    private void initListFourView() {
        initTitleView(4);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.viewType.typeList4) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        mAdapters.add(adapter);
        initMoreView(4);
    }


    private void initListFiveView() {
        initTitleView(5);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, gridLayoutHelper, R.layout.view_vlayout_grid, 6, Constant.viewType.typeGvBottom) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView ivImage = holder.getView(R.id.iv_image);
                ivImage.setImageResource(R.drawable.bg_small_leaves_min);
            }
        };
        mAdapters.add(adapter);
        initMoreView(4);
    }


    private void initListSixView() {
        initTitleView(6);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity, linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.viewType.typeList5) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        mAdapters.add(adapter);
        initMoreView(6);
    }


    private void initTitleView(final int type) {
        BaseDelegateAdapter titleAdapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.view_vlayout_title, 1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                switch (type) {
                    case 1:
                        holder.setText(R.id.tv_title, "为你精选");
                        break;
                    case 2:
                        holder.setText(R.id.tv_title, "推广专区");
                        break;
                    case 3:
                        holder.setText(R.id.tv_title, "行业动态");
                        break;
                    case 4:
                        holder.setText(R.id.tv_title, "趋势分析");
                        break;
                    case 5:
                        holder.setText(R.id.tv_title, "大牛分享");
                        break;
                    case 6:
                        holder.setText(R.id.tv_title, "潇湘剑雨");
                        break;
                    default:
                        holder.setText(R.id.tv_title, "这个是标题");
                        break;
                }
                holder.getView(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type) {
                            case 1:
                                ToastUtil.showToast(activity, "刷新为你精选数据");
                                break;
                            case 2:
                                ToastUtil.showToast(activity, "刷新推广专区数据");
                                break;
                            case 3:
                                ToastUtil.showToast(activity, "刷新行业动态数据");
                                break;
                            case 4:
                                ToastUtil.showToast(activity, "刷新趋势分析数据");
                                break;
                            case 5:
                                ToastUtil.showToast(activity, "刷新大牛分享数据");
                                break;
                            case 6:
                                ToastUtil.showToast(activity, "刷新潇湘剑雨的数据");
                                break;
                            default:
                                ToastUtil.showToast(activity, "刷新XXXX数据");
                                break;
                        }
                    }
                });
            }
        };
        mAdapters.add(titleAdapter);
    }


    private void initMoreView(final int type) {
        BaseDelegateAdapter moreAdapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(), R.layout.view_vlayout_more, 1, Constant.viewType.typeMore) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                switch (type) {
                    case 1:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 2:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 3:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 4:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 5:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 6:
                        holder.setText(R.id.tv_more, "没有更多数据");
                        break;
                    default:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                }
                holder.getView(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type) {
                            case 1:
                                ToastUtil.showToast(activity, "跳转为你精选数据");
                                break;
                            case 2:
                                ToastUtil.showToast(activity, "跳转推广专区数据");
                                break;
                            case 3:
                                ToastUtil.showToast(activity, "跳转行业动态数据");
                                break;
                            case 4:
                                ToastUtil.showToast(activity, "跳转趋势分析数据");
                                break;
                            case 5:
                                ToastUtil.showToast(activity, "跳转大牛分享数据");
                                break;
                            default:
                                ToastUtil.showToast(activity, "跳转XXXX数据");
                                break;
                        }
                    }
                });
            }
        };
        mAdapters.add(moreAdapter);
    }


}
