package org.yczbj.ycvideoplayerlib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Gravity;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.yczbj.ycvideoplayerlib.inter.listener.OnSurfaceListener;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 重写TextureView，适配视频的宽高和旋转
 *     revise:
 * </pre>
 */
@SuppressLint("NewApi")
public class VideoTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private int videoHeight;
    private int videoWidth;
    private OnSurfaceListener onSurfaceListener;


    public VideoTextureView(Context context) {
        super(context);
    }


    /**
     * SurfaceTexture准备就绪
     * @param surface                   surface
     * @param width                     width
     * @param height                    height
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (onSurfaceListener != null) {
            onSurfaceListener.onSurfaceAvailable(surface);
        }
    }


    /**
     * SurfaceTexture缓冲大小变化
     * @param surface                   surface
     * @param width                     width
     * @param height                    height
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (onSurfaceListener != null) {
            onSurfaceListener.onSurfaceSizeChanged(surface, width, height);
        }
    }


    /**
     * SurfaceTexture即将被销毁
     * @param surface                   surface
     */
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //清空释放
        if (onSurfaceListener != null) {
            onSurfaceListener.onSurfaceDestroyed(surface);
        }
        return false;
    }


    /**
     * SurfaceTexture通过updateImage更新
     * @param surface                   surface
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //如果播放的是暂停全屏了
        if (onSurfaceListener != null) {
            onSurfaceListener.onSurfaceUpdated(surface);
        }
    }


    /**
     * 获取listener
     * @return                          onSurfaceListener
     */
    public OnSurfaceListener getOnSurfaceListener() {
        return onSurfaceListener;
    }


    /**
     * 设置监听
     * @param surfaceListener           onSurfaceListener
     */
    public void setOnSurfaceListener(OnSurfaceListener surfaceListener) {
        setSurfaceTextureListener(this);
        onSurfaceListener = surfaceListener;
    }


    /**
     * 添加TextureView到视图中
     * @param frameLayout               布局
     * @param textureView               textureView
     */
    public void addTextureView(FrameLayout frameLayout , VideoTextureView textureView){
        frameLayout.removeView(textureView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        frameLayout.addView(textureView, 0, params);
    }




    /**
     * 自定义video大小
     * @param videoWidth                宽
     * @param videoHeight               高
     */
    public void adaptVideoSize(int videoWidth, int videoHeight) {
        if (this.videoWidth != videoWidth && this.videoHeight != videoHeight) {
            this.videoWidth = videoWidth;
            this.videoHeight = videoHeight;
            requestLayout();
        }
    }


    /**
     * 记得一定要重新写这个方法，如果角度发生了变化，就重新绘制布局
     * 设置视频旋转角度
     * @param rotation                  角度
     */
    @Override
    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            super.setRotation(rotation);
            requestLayout();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float viewRotation = getRotation();
        // 如果判断成立，则说明显示的TextureView和本身的位置是有90度的旋转的，所以需要交换宽高参数。
        float viewRotation1 = 90f;
        float viewRotation2 = 270f;
        if (viewRotation == viewRotation1 || viewRotation == viewRotation2) {
            int tempMeasureSpec = widthMeasureSpec;
            //noinspection SuspiciousNameCombination
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
        }

        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        if (videoWidth > 0 && videoHeight > 0) {

            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize;
                height = heightSpecSize;
                // for compatibility, we adjust size based on aspect ratio
                if (videoWidth * height < width * videoHeight) {
                    width = height * videoWidth / videoHeight;
                } else if (videoWidth * height > width * videoHeight) {
                    height = width * videoHeight / videoWidth;
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize;
                height = width * videoHeight / videoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize;
                width = height * videoWidth / videoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = videoWidth;
                height = videoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }
        setMeasuredDimension(width, height);
    }


}
