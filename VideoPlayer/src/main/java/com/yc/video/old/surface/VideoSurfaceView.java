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

import android.content.Context;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/9/21
 *     desc  : 重写SurfaceView，适配视频的宽高和旋转
 *     revise:
 * </pre>
 */
@Deprecated
public class VideoSurfaceView extends SurfaceView{


    /**
     * 优点：可以在一个独立的线程中进行绘制，不会影响主线程；使用双缓冲机制，播放视频时画面更流畅
     * 缺点：Surface不在View hierachy中，它的显示也不受View的属性控制，所以不能进行平移，缩放等变换，
     *      也不能放在其它ViewGroup中。SurfaceView 不能嵌套使用。
     */

    private int videoHeight;
    private int videoWidth;
    private OnSurfaceListener onSurfaceListener;
    private static final float EQUAL_FLOAT = 0.0000001f;

    public VideoSurfaceView(Context context) {
        super(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (callback!=null){
            getHolder().removeCallback(callback);
        }
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback(){
        /**
         * 创建的时候调用该方法
         * @param holder                        holder
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (onSurfaceListener!=null){
                onSurfaceListener.surfaceCreated(holder);
            }
        }

        /**
         * 视图改变的时候调用方法
         * @param holder
         * @param format
         * @param width
         * @param height
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (onSurfaceListener!=null){
                onSurfaceListener.surfaceChanged(holder, format, width, height);
            }
        }

        /**
         * 销毁的时候调用该方法
         * @param holder
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (onSurfaceListener!=null){
                onSurfaceListener.surfaceDestroyed(holder);
            }
        }
    };


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
        this.onSurfaceListener = surfaceListener;
        getHolder().addCallback(callback);
    }


    /**
     * 添加TextureView到视图中
     * @param frameLayout               布局
     * @param videoSurfaceView          videoSurfaceView
     */
    public void addTextureView(FrameLayout frameLayout , VideoSurfaceView videoSurfaceView){
        frameLayout.removeView(videoSurfaceView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        frameLayout.addView(videoSurfaceView, 0, params);
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
        } else {
            // no size yet, just adopt the given spec sizes
        }
        setMeasuredDimension(width, height);
    }

    public interface OnSurfaceListener {

        void surfaceCreated(SurfaceHolder holder);

        void surfaceChanged(SurfaceHolder holder, int format, int width, int height);

        void surfaceDestroyed(SurfaceHolder holder);

    }
}
