package org.yczbj.ycvideoplayer.ui.test.view.second;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.listener.OnListItemClickListener;
import org.yczbj.ycvideoplayer.ui.test.model.VideoConstant;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class TestListFiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "AdapterRecyclerViewVideo";
    private List<TestFiveBean> list;
    private Context context;

    TestListFiveAdapter(Context context, List<TestFiveBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            return new MyViewHolder(LayoutInflater.from(context).inflate(
                    R.layout.item_test_list_videoview, parent, false));
        }else if(viewType == 2){
            return new TextViewViewHolder(LayoutInflater.from(context).inflate(
                    R.layout.item_test_list_textview, parent, false));
        }else {
            return new TextViewViewHolder(LayoutInflater.from(context).inflate(
                    R.layout.item_test_list_textview, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).jzVideoPlayer.setUp(
                    VideoConstant.videoUrls[0][position], JZVideoPlayer.SCREEN_WINDOW_LIST,
                    VideoConstant.videoTitles[0][position]);
            Picasso.with(((MyViewHolder) holder).jzVideoPlayer.getContext())
                    .load(VideoConstant.videoThumbs[0][position])
                    .into(((MyViewHolder) holder).jzVideoPlayer.thumbImageView);
        }else if(holder instanceof TextViewViewHolder){
             ((TextViewViewHolder) holder).textview.setText("这个是文本控件");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener!=null){
                    mItemClickListener.onItemClick(view,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getType()==1){
            return 1;
        }else if(list.get(position).getType()==2){
            return 2;
        }else {
            return 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        JZVideoPlayerStandard jzVideoPlayer;

        MyViewHolder(View itemView) {
            super(itemView);
            jzVideoPlayer = (JZVideoPlayerStandard)itemView.findViewById(R.id.videoplayer);
        }
    }

    class TextViewViewHolder extends RecyclerView.ViewHolder {

        TextView textview;

        TextViewViewHolder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.textview);
        }
    }

    private OnListItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnListItemClickListener listener) {
        this.mItemClickListener = listener;
    }


}
