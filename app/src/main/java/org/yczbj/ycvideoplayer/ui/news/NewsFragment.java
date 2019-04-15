package org.yczbj.ycvideoplayer.ui.news;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.Utils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BasePagerAdapter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseLazyFragment;
import org.yczbj.ycvideoplayer.base.mvp2.BaseList1Fragment;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;
import org.yczbj.ycvideoplayer.ui.news.view.fragment.NewsArticleFragment;
import org.yczbj.ycvideoplayer.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by yc on 2018/2/28.
 *
 */

public class NewsFragment extends BaseLazyFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MainActivity activity;

    private List<Fragment> fragmentList = new ArrayList<>();
    private BasePagerAdapter adapter;
    private String[] categoryId;
    private String[] categoryName;

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
    public void onResume() {
        super.onResume();
        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
    }


    @Override
    public int getContentView() {
        return R.layout.base_tab_view;
    }

    @Override
    public void initView() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor());
        viewPager.setOffscreenPageLimit(10);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        categoryId = Utils.getContext().getResources().getStringArray(R.array.mobile_news_id);
        categoryName = Utils.getContext().getResources().getStringArray(R.array.mobile_news_name);
    }

    @Override
    public void onLazyLoad() {
        ArrayList<String> title = new ArrayList<>();
        if(categoryId.length<=15){
            return;
        }
        for (int i = 0; i < 15; i++) {
            Fragment fragment = NewsArticleFragment.newInstance(categoryId[i]);
            fragmentList.add(fragment);
            title.add(categoryName[i]);
        }
        adapter = new BasePagerAdapter(getChildFragmentManager(), fragmentList,title);
        viewPager.setAdapter(adapter);
    }

    public void onDoubleClick() {
        if (fragmentList != null && fragmentList.size() > 0) {
            int item = viewPager.getCurrentItem();
            ((BaseList1Fragment) fragmentList.get(item)).onRefresh();
        }
    }

}
