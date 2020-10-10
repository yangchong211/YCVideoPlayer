package org.yczbj.ycvideoplayerlib.view.states;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import org.yczbj.ycvideoplayerlib.ui.CustomGuideView;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/10/21
 *     desc  : 状态操作层，负责各个视图view的状态切换
 *     revise:
 * </pre>
 */
public class StatesPlayerController extends RelativeLayout {

    private Context context;
    private CustomGuideView mGuideView;

    public StatesPlayerController(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public StatesPlayerController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StatesPlayerController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        //初始化播放用的surfaceView
        //initSurfaceView();
        //初始化控制栏
        //initBasisControlView();
        //初始化引导图视图
        //initGuideView();
        //初始化播放器
        //initVideoPlayer();
        //初始化封面
        //initCoverView();
        //初始化手势view
        //initGestureView();
        //初始化清晰度view
        //initQualityView();
        //初始化缩略图
        //initThumbnailView();
        //初始化倍速view
        //initSpeedView();
        //初始化指引view
        initGuideView();
        //初始化提示view
        //initTipsView();
        //初始化网络监听器
        //initNetWatchdog();
        //初始化屏幕方向监听
        //initOrientationWatchdog();
        //初始化手势对话框控制
        //initGestureDialogManager();

        //直播
        boolean isZhiBo = true;
        if (isZhiBo){
            //初始化指引view
            //initGuideView();
            //初始化提示view
            //initTipsView();
            //初始化网络监听器
            //initNetWatchdog();
        }

        //投屏
        boolean isScreen = true;
        if (isScreen){
            //初始化指引view
            //initGuideView();
            //初始化提示view
            //initTipsView();
            //初始化网络监听器
            //initNetWatchdog();
        }
    }

    /**
     * addSubView 添加子view到布局中
     * @param view                          子view
     */
    private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //添加到布局中
        addView(view, params);
    }

    /**
     * 添加子View到布局中央
     * @param view                          子view
     */
    private void addSubViewByCenter(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, params);
    }

    /**
     * 添加子View到布局中,在某个View的下方
     * @param view                          需要添加的View
     * @param belowTargetView               在这个View的下方
     */
    private void addSubViewBelow(final View view, final View belowTargetView) {
        belowTargetView.post(new Runnable() {
            @Override
            public void run() {
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                int measuredHeight = belowTargetView.getMeasuredHeight();
                params.topMargin = measuredHeight;
                //添加到布局中
                addView(view, params);
            }
        });
    }

    /**
     * 初始化引导view
     */
    private void initGuideView() {
        mGuideView = new CustomGuideView(getContext());
        addSubView(mGuideView);
        //切换状态的操作逻辑，调用api即可
        mGuideView.hide();
        mGuideView.setScreenMode(1);
    }


}
