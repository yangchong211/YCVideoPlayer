package org.yczbj.ycvideoplayer.ui.video.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.Utils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BasePagerAdapter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseFragment;
import org.yczbj.ycvideoplayer.base.mvp2.BaseList1Fragment;
import org.yczbj.ycvideoplayer.ui.video.view.fragment.VideoArticleFragment;
import org.yczbj.ycvideoplayer.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author yc
 * @date 2017/12/29
 */
public class VideoFragment extends BaseFragment {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String categoryId[] = Utils.getContext().getResources().getStringArray(R.array.mobile_video_id);
    private String categoryName[] = Utils.getContext().getResources().getStringArray(R.array.mobile_video_name);
    private List<Fragment> fragmentList = new ArrayList<>();
    private BasePagerAdapter adapter;

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
        viewPager.setOffscreenPageLimit(categoryId.length);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        ArrayList<String> title = new ArrayList<>();
        for (int i = 0; i < categoryId.length; i++) {
            Fragment fragment = VideoArticleFragment.newInstance(categoryId[i]);
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
