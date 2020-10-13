package org.yczbj.ycvideoplayer.oldPlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.yczbj.ycvideoplayer.ImageUtil;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.old.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.old.player.OldVideoPlayer;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private List<Video> mVideoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_my_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(itemView);
        //创建视频播放控制器，主要只要创建一次就可以呢
        VideoPlayerController controller = new VideoPlayerController(mContext);
        holder.setController(controller);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        holder.bindData(video);
    }

    @Override
    public int getItemCount() {
        return mVideoList==null ? 0 : mVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoPlayerController mController;
        public OldVideoPlayer mVideoPlayer;

        VideoViewHolder(View itemView) {
            super(itemView);
            mVideoPlayer = (OldVideoPlayer) itemView.findViewById(R.id.nice_video_player);
            // 将列表中的每个视频设置为默认16:9的比例
            ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
            // 宽度为屏幕宽度
            params.width = itemView.getResources().getDisplayMetrics().widthPixels;
            // 高度为宽度的9/16
            params.height = (int) (params.width * 9f / 16f);
            mVideoPlayer.setLayoutParams(params);
        }

        /**
         * 设置视频控制器参数
         * @param controller            控制器对象
         */
        void setController(VideoPlayerController controller) {
            mController = controller;
            mVideoPlayer.setPlayerType(ConstantKeys.VideoPlayerType.TYPE_IJK);
            mVideoPlayer.setController(mController);
        }

        void bindData(Video video) {
            mController.setTitle(video.getTitle());
            //mController.setLength(video.getLength());
            ImageUtil.display(itemView.getContext(),video.getUrl(),R.drawable.image_default,mController.imageView());
            mVideoPlayer.setUp(video.getUrl(), null);
        }
    }


}
