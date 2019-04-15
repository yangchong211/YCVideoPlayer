package org.yczbj.ycvideoplayer.ui.movie.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieAdapter extends RecyclerArrayAdapter<MovieBean.RetBean.ListBean.ChildListBean> {

    public MovieAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(parent);
    }

    public class MovieViewHolder extends BaseViewHolder<MovieBean.RetBean.ListBean.ChildListBean> {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_content)
        TextView tvContent;

        MovieViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_movie_news);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void setData(MovieBean.RetBean.ListBean.ChildListBean data) {
            super.setData(data);
            if (data != null) {
                ImageUtil.loadImgByPicasso(getContext(), data.getPic(), R.drawable.image_default, ivImage);
                tvTitle.setText(data.getTitle());
                tvTime.setText("影片上映日期：" + data.getAirTime());
                tvDuration.setText(data.getDuration());
            }
        }
    }


}
