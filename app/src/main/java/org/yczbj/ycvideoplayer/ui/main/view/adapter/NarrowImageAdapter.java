package org.yczbj.ycvideoplayer.ui.main.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.main.model.VideoPlayerFavorite;


public class NarrowImageAdapter extends RecyclerArrayAdapter<VideoPlayerFavorite> {


    public NarrowImageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NarrowImageViewHolder(parent);
    }

    private static class NarrowImageViewHolder extends BaseViewHolder<VideoPlayerFavorite> {

        NarrowImageViewHolder(ViewGroup parent) {
            super(parent, R.layout.view_video_player_favorite);
        }

        @Override
        public void setData(VideoPlayerFavorite data) {

        }
    }
}
