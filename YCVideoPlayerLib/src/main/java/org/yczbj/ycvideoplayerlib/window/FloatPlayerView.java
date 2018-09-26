package org.yczbj.ycvideoplayerlib.window;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.inter.listener.OnCompletedListener;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.utils.VideoLogUtil;


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

    private VideoPlayer mVideoPlayer;

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
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view ;
        if (inflater != null) {
            view = inflater.inflate(R.layout.view_window_dialog, this);
            mVideoPlayer = view.findViewById(R.id.video_player);
            mVideoPlayer.setUp(path,null);
            mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
            //创建视频控制器
            VideoPlayerController controller = new VideoPlayerController(getContext());
            controller.setTopVisibility(false);
            controller.setLoadingType(ConstantKeys.Loading.LOADING_QQ);
            controller.imageView().setBackgroundColor(Color.BLACK);
            controller.setCenterPlayer(true,R.drawable.icon_play_center);
            controller.setOnCompletedListener(new OnCompletedListener() {
                @Override
                public void onCompleted() {
                    VideoPlayerManager.instance().releaseVideoPlayer();
                    if(mCompletedListener!=null){
                        mCompletedListener.Completed();
                    }
                }
            });
            mVideoPlayer.setController(controller);
            mVideoPlayer.start();
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoLogUtil.d("点击事件"+mVideoPlayer.getCurrentState());
                    if(mVideoPlayer.isPlaying()){
                        mVideoPlayer.pause();
                    }else if(mVideoPlayer.isPaused()){
                        mVideoPlayer.restart();
                    }
                    VideoLogUtil.d("点击事件"+mVideoPlayer.getCurrentState());
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
