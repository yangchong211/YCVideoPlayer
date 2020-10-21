/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.yc.video.old.surface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Gravity;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.yc.kernel.utils.VideoLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 重写TextureView，适配视频的宽高和旋转
 *     revise:
 * </pre>
 */
@Deprecated
@SuppressLint("NewApi")
public class VideoTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    /**
     * 优点：支持移动、旋转、缩放等动画，支持截图。具有view的属性
     * 缺点：必须在硬件加速的窗口中使用，占用内存比SurfaceView高，在5.0以前在主线程渲染，5.0以后有单独的渲染线程。
     */

    private int videoHeight;
    private int videoWidth;
    private OnTextureListener onTextureListener;
    private static final float EQUAL_FLOAT = 0.0000001f;


    public VideoTextureView(Context context) {
        super(context);
    }


    /**
     * SurfaceTexture准备就绪
     * @param surface                   surface
     * @param width                     WIDTH
     * @param height                    HEIGHT
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (onTextureListener != null) {
            onTextureListener.onSurfaceAvailable(surface);
        }
    }


    /**
     * SurfaceTexture缓冲大小变化
     * @param surface                   surface
     * @param width                     WIDTH
     * @param height                    HEIGHT
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (onTextureListener != null) {
            onTextureListener.onSurfaceSizeChanged(surface, width, height);
        }
    }


    /**
     * SurfaceTexture即将被销毁
     * @param surface                   surface
     */
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //清空释放
        if (onTextureListener != null) {
            onTextureListener.onSurfaceDestroyed(surface);
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
        if (onTextureListener != null) {
            onTextureListener.onSurfaceUpdated(surface);
        }
    }


    /**
     * 获取listener
     * @return                          onTextureListener
     */
    public OnTextureListener getonTextureListener() {
        return onTextureListener;
    }


    /**
     * 设置监听
     * @param surfaceListener           onTextureListener
     */
    public void setOnTextureListener(OnTextureListener surfaceListener) {
        setSurfaceTextureListener(this);
        onTextureListener = surfaceListener;
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
        // 获取视图旋转的角度
        float viewRotation = getRotation();
        // 如果判断成立，则说明显示的TextureView和本身的位置是有90度的旋转的，所以需要交换宽高参数。
        float viewRotation1 = 90f;
        float viewRotation2 = 270f;
        //如果是横竖屏旋转切换视图，则宽高属性互换
        if (Math.abs(viewRotation-viewRotation1)> EQUAL_FLOAT && Math.abs(viewRotation1-viewRotation)> EQUAL_FLOAT ||
                (Math.abs(viewRotation-viewRotation2)> EQUAL_FLOAT && Math.abs(viewRotation2-viewRotation)> EQUAL_FLOAT)){
            int tempMeasureSpec = widthMeasureSpec;
            //noinspection SuspiciousNameCombination
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
            VideoLogUtils.d("TextureView---------"+"如果是横竖屏旋转切换视图，则宽高属性互换");
        }
        /*if (viewRotation == viewRotation1 || viewRotation == viewRotation2) {
            int tempMeasureSpec = widthMeasureSpec;
            //noinspection SuspiciousNameCombination
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
        }*/
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
                // only the WIDTH is fixed, adjust the HEIGHT to match aspect ratio if possible
                width = widthSpecSize;
                height = width * videoHeight / videoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the HEIGHT is fixed, adjust the WIDTH to match aspect ratio if possible
                height = heightSpecSize;
                width = height * videoWidth / videoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            } else {
                // neither the WIDTH nor the HEIGHT are fixed, try to use actual video size
                width = videoWidth;
                height = videoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both WIDTH and HEIGHT
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both WIDTH and HEIGHT
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            }
        }  // no size yet, just adopt the given spec sizes
        setMeasuredDimension(width, height);
    }

    public interface OnTextureListener {

        /**
         * SurfaceTexture准备就绪
         * @param surface                   surface
         */
        void onSurfaceAvailable(SurfaceTexture surface);

        /**
         * SurfaceTexture缓冲大小变化
         * @param surface                   surface
         * @param width                     WIDTH
         * @param height                    HEIGHT
         */
        void onSurfaceSizeChanged(SurfaceTexture surface, int width, int height);

        /**
         * SurfaceTexture即将被销毁
         * @param surface                   surface
         * @return                          销毁
         */
        boolean onSurfaceDestroyed(SurfaceTexture surface);

        /**
         * SurfaceTexture通过updateImage更新
         * @param surface                   surface
         */
        void onSurfaceUpdated(SurfaceTexture surface);

    }
}
