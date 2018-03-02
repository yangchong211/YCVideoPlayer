package org.yczbj.ycvideoplayer.ui.test.test1.view.second;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.model.VideoConstant;

import butterknife.Bind;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Update:
 * CreatedTime:2017/12/29
 * Author:yc
 */
public class TestListFirstActivity extends BaseActivity {


    @Bind(R.id.listView)
    ListView listView;
    SensorManager sensorManager;
    JZVideoPlayer.JZAutoFullscreenListener sensorEventListener;

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_list_first;
    }

    @Override
    public void initView() {
        listView.setAdapter(new AdapterVideoList(this,
                VideoConstant.videoUrls[0],
                VideoConstant.videoTitles[0],
                VideoConstant.videoThumbs[0]));

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                JZVideoPlayer.onScrollReleaseAllVideos(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorEventListener = new JZVideoPlayer.JZAutoFullscreenListener();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    public static class AdapterVideoList extends BaseAdapter {

        public static final String TAG = "JiaoZiVideoPlayer";
        Context context;
        String[] videoUrls;
        String[] videoTitles;
        String[] videoThumbs;

        AdapterVideoList(Context context, String[] videoUrls, String[] videoTitles, String[] videoThumbs) {
            this.context = context;
            this.videoUrls = videoUrls;
            this.videoTitles = videoTitles;
            this.videoThumbs = videoThumbs;
        }

        @Override
        public int getCount() {
            return videoUrls.length;
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
            ViewHolder viewHolder;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(R.layout.item_test_list_videoview, null);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.jzVideoPlayer = (JZVideoPlayerStandard)convertView.findViewById(R.id.videoplayer);
            viewHolder.jzVideoPlayer.setUp(
                    videoUrls[position], JZVideoPlayer.SCREEN_WINDOW_LIST,
                    videoTitles[position]);
            Picasso.with(convertView.getContext())
                    .load(videoThumbs[position])
                    .into(viewHolder.jzVideoPlayer.thumbImageView);

            viewHolder.jzVideoPlayer.positionInList = position;
            return convertView;
        }

        class ViewHolder {
            JZVideoPlayerStandard jzVideoPlayer;
        }
    }


}
