package org.yczbj.ycvideoplayer.ui.movie.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieDetailBean;
import org.yczbj.ycvideoplayer.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailAdapter extends RecyclerArrayAdapter<MovieDetailBean.RetBean.ListBean.ChildListBean> {

    public MovieDetailAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(parent);
    }

    public class MovieViewHolder extends BaseViewHolder<MovieDetailBean.RetBean.ListBean.ChildListBean> {


        @BindView(R.id.iv_movie_photo)
        ImageView ivMoviePhoto;
        @BindView(R.id.tv_movie_title)
        TextView tvMovieTitle;
        @BindView(R.id.tv_movie_directors)
        TextView tvMovieDirectors;
        @BindView(R.id.tv_movie_casts)
        TextView tvMovieCasts;
        @BindView(R.id.tv_movie_genres)
        TextView tvMovieGenres;

        MovieViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_movie_detail_news);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void setData(MovieDetailBean.RetBean.ListBean.ChildListBean data) {
            super.setData(data);
            if (data != null) {
                ImageUtil.loadImgByPicasso(getContext(), data.getPic(), R.drawable.image_default, ivMoviePhoto);
                tvMovieTitle.setText(data.getTitle());
                tvMovieDirectors.setText("潇湘剑雨");
                tvMovieCasts.setText("时间"+data.getAirTime());
                tvMovieGenres.setText(data.getDescription());
            }
        }


    }


}
