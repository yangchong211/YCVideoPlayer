package org.yczbj.ycvideoplayer.test.test1.view.second;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.test.test1.model.VideoConstant;

import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestListThirdActivity extends BaseActivity {

    @Bind(R.id.listView)
    ListView listView;
    private VideoListAdapter mAdapter;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_list_first;
    }

    @Override
    public void initView() {
        mAdapter = new VideoListAdapter(this);
        listView.setAdapter(mAdapter);
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
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    public class VideoListAdapter extends BaseAdapter {

        int[] viewtype = {0, 0, 0, 1, 0, 0, 0, 1, 0, 0};//1 = jzvd, 0 = textView

        Context context;
        LayoutInflater mInflater;

        VideoListAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return viewtype.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == 1) {
                VideoListAdapter.VideoHolder viewHolder;
                if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoListAdapter.VideoHolder) {
                    viewHolder = (VideoListAdapter.VideoHolder) convertView.getTag();
                } else {
                    viewHolder = new VideoListAdapter.VideoHolder();
                    convertView = mInflater.inflate(R.layout.item_test_list_videoview, null);
                    viewHolder.jzVideoPlayer = (JZVideoPlayerStandard)convertView.findViewById(R.id.videoplayer);
                    convertView.setTag(viewHolder);
                }

                viewHolder.jzVideoPlayer.setUp(
                        VideoConstant.videoUrls[0][position], JZVideoPlayer.SCREEN_WINDOW_LIST,
                        VideoConstant.videoTitles[0][position]);
                viewHolder.jzVideoPlayer.positionInList = position;
                Picasso.with(TestListThirdActivity.this)
                        .load(VideoConstant.videoThumbs[0][position])
                        .into(viewHolder.jzVideoPlayer.thumbImageView);
            } else {
                VideoListAdapter.TextViewHolder textViewHolder;
                if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoListAdapter.TextViewHolder) {
                    textViewHolder = (VideoListAdapter.TextViewHolder) convertView.getTag();
                } else {
                    textViewHolder = new VideoListAdapter.TextViewHolder();
                    LayoutInflater mInflater = LayoutInflater.from(context);
                    convertView = mInflater.inflate(R.layout.item_test_list_textview, null);
                    textViewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                    convertView.setTag(textViewHolder);
                }
            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return viewtype[position];
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class VideoHolder {
            JZVideoPlayerStandard jzVideoPlayer;
        }

        class TextViewHolder {
            TextView textView;
        }
    }

}
