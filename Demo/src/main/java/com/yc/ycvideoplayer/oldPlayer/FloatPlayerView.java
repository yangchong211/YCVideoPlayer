package com.yc.ycvideoplayer.oldPlayer;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yc.kernel.utils.VideoLogUtils;
import com.yc.video.config.ConstantKeys;
import com.yc.video.old.controller.VideoPlayerController;
import com.yc.video.old.listener.OnPlayerStatesListener;
import com.yc.video.old.other.VideoPlayerManager;
import com.yc.video.old.player.OldVideoPlayer;
import com.yc.videoview.SmallWindowTouch;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/8/29
 *     desc  : 适配了悬浮窗的view
 *     revise:
 * </pre>
 */
public class FloatPlayerView extends FrameLayout {

    private OldVideoPlayer mVideoPlayer;

    public FloatPlayerView(Context context) {
        super(context);
        init();
    }

    public FloatPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view ;
        if (inflater != null) {
            view = inflater.inflate(com.yc.video.R.layout.old_view_window_dialog, this);
            mVideoPlayer = view.findViewById(com.yc.video.R.id.video_player);
            mVideoPlayer.setUp(path,null);
            mVideoPlayer.setPlayerType(ConstantKeys.VideoPlayerType.TYPE_IJK);
            //创建视频控制器
            VideoPlayerController controller = new VideoPlayerController(getContext());
            controller.setTopVisibility(false);
            controller.setLoadingType(ConstantKeys.Loading.LOADING_QQ);
            controller.imageView().setBackgroundColor(Color.BLACK);
            controller.setOnPlayerStatesListener(new OnPlayerStatesListener() {
                @Override
                public void onPlayerStates(int states) {
                    if (states == ConstantKeys.PlayerStatesType.COMPLETED){
                        VideoPlayerManager.instance().releaseVideoPlayer();
                        if(mCompletedListener!=null){
                            mCompletedListener.Completed();
                        }
                    }
                }
            });
            //controller.onPlayModeChanged(ConstantKeys.PlayMode.MODE_TINY_WINDOW);
            mVideoPlayer.setController(controller);
            //mVideoPlayer.enterTinyWindow();
            mVideoPlayer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mVideoPlayer.start();
                }
            },300);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoLogUtils.d("点击事件"+mVideoPlayer.getCurrentState());
                    if(mVideoPlayer.isPlaying()){
                        mVideoPlayer.pause();
                    }else if(mVideoPlayer.isPaused()){
                        mVideoPlayer.restart();
                    }
                    VideoLogUtils.d("点击事件"+mVideoPlayer.getCurrentState());
                }
            });
            view.setOnTouchListener(new SmallWindowTouch(view,0,0));
        }
    }

    private static String path;
    public static void setUrl(String url) {
        path = url;
    }

    public interface CompletedListener{
        /**
         * 播放完成
         */
        void Completed();
    }

    /**
     * 监听视频播放完成事件
     */
    private CompletedListener mCompletedListener;
    public void setCompletedListener(CompletedListener listener){
        this.mCompletedListener = listener;
    }

}
