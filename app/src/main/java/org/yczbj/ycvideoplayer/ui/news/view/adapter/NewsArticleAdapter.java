package org.yczbj.ycvideoplayer.ui.news.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.home.model.VideoPlayerComment;


public class NewsArticleAdapter extends RecyclerArrayAdapter<VideoPlayerComment> {

    public NewsArticleAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoPlayerViewHolder(parent);
    }


    private class VideoPlayerViewHolder extends BaseViewHolder<VideoPlayerComment> {

        ImageView iv_movie_photo;
        TextView tv_movie_title , tv_movie_directors ,tv_movie_casts,tv_movie_genres ,tv_movie_rating_rate;
        View view_color;

        VideoPlayerViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video_player_com);
        }

        @Override
        public void setData(VideoPlayerComment data) {
            super.setData(data);

        }
    }
}
