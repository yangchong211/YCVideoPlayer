package org.yczbj.ycvideoplayer.ui.movie.view.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.special.model.SpecialBean;

public class MovieNewsAdapter extends RecyclerArrayAdapter<SpecialBean> {

    public MovieNewsAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpressDeliveryViewHolder(parent);
    }

    private class ExpressDeliveryViewHolder extends BaseViewHolder<SpecialBean> {

        ImageView iv_topic_face;
        TextView tv_topic_name , tv_topic_tips ,tv_topic_comment , tv_topic_node ,tv_topic_title;

        ExpressDeliveryViewHolder(ViewGroup parent) {
            super(parent, R.layout.view_vlayout_news);

        }

        @Override
        public void setData(SpecialBean data) {
            super.setData(data);

        }
    }
}
