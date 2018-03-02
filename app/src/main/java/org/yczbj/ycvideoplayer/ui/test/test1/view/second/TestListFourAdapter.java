package org.yczbj.ycvideoplayer.ui.test.test1.view.second;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.test.test1.model.VideoConstant;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class TestListFourAdapter extends RecyclerView.Adapter<TestListFourAdapter.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerViewVideo";
    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Context context;

    public TestListFourAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_test_list_videoview, parent,
                false));
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzVideoPlayer.hashCode() + "] position=" + position);
        holder.jzVideoPlayer.setUp(
                VideoConstant.videoUrls[0][position], JZVideoPlayer.SCREEN_WINDOW_LIST,
                VideoConstant.videoTitles[0][position]);
        Picasso.with(holder.jzVideoPlayer.getContext())
                .load(VideoConstant.videoThumbs[0][position])
                .into(holder.jzVideoPlayer.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return videoIndexs.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard jzVideoPlayer;

        MyViewHolder(View itemView) {
            super(itemView);
            jzVideoPlayer = (JZVideoPlayerStandard)itemView.findViewById(R.id.videoplayer);
        }
    }

}
