package org.yczbj.ycvideoplayer.ui.movie.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.blankj.utilcode.util.Utils;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BasePagerAdapter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.movie.view.fragment.MovieNewsFragment;
import org.yczbj.ycvideoplayer.util.SettingUtil;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yc on 2018/3/6.
 *
 */

public class MovieNewsActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

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
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        String[] movieNews = Utils.getContext().getResources().getStringArray(R.array.movie_news);
        ArrayList<String> title = new ArrayList<>();
        for (int i = 0; i < movieNews.length; i++) {
            Fragment fragment = MovieNewsFragment.newInstance(movieNews[i]);
            fragmentList.add(fragment);
            title.add(movieNews[i]);
        }
        BasePagerAdapter adapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList, title);
        viewPager.setAdapter(adapter);
    }

}
