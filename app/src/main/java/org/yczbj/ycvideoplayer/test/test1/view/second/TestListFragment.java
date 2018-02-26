package org.yczbj.ycvideoplayer.test.test1.view.second;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.test.test1.model.VideoConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * Created by Nathen on 2017/6/9.
 */
public class TestListFragment extends Fragment {

    int index;
    @Bind(R.id.listView)
    ListView listView;

    public TestListFragment setIndex(int index) {
        this.index = index;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState) {
        View view = inflater.inflate(R.layout.activity_test_list_first, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(new TestListFirstActivity.AdapterVideoList(getActivity(),
                VideoConstant.videoUrls[index],
                VideoConstant.videoTitles[index],
                VideoConstant.videoThumbs[index]));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                JZVideoPlayer.onScrollReleaseAllVideos(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
