package org.yczbj.ycvideoplayer.ui.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ns.yc.ycprogresslib.CircleProgressbar;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.home.model.DialogListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<DialogListBean> mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private ViewHolder holder;

    public DialogListAdapter(Context mContext, List<DialogListBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new ViewHolder(inflater.inflate(R.layout.item_dialog_list_view, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvTime.setText("时长98:00：12");
        holder.tvVideoSize.setText("100MB");
        holder.ivDownload.setImageResource(R.drawable.icon_cache_download);

        //设置类型
        holder.circlePb.setProgressType(CircleProgressbar.ProgressType.COUNT);
        //设置圆形的填充颜色
        holder.circlePb.setInCircleColor(mContext.getResources().getColor(R.color.colorTransparent));
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineColor(mContext.getResources().getColor(R.color.gray3));
        //设置进度监听
        holder.circlePb.setCountdownProgressListener(1, progressListener);
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineWidth(1);
        //设置进度条线的宽度
        holder.circlePb.setProgressLineWidth(2);
        //设置进度
        holder.circlePb.setProgress(50);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_download)
        ImageView ivDownload;
        @BindView(R.id.circle_pb)
        CircleProgressbar circlePb;
        @BindView(R.id.tv_video_size)
        TextView tvVideoSize;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                holder.circlePb.setText("0");
            }
        }
    };


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }
}
