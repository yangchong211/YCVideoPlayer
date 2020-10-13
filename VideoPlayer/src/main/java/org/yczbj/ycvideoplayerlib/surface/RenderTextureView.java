package org.yczbj.ycvideoplayerlib.surface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.kernel.inter.AbstractVideoPlayer;


@SuppressLint("ViewConstructor")
public class RenderTextureView extends TextureView implements ISurfaceView,
        TextureView.SurfaceTextureListener {

    private MeasureHelper mMeasureHelper;
    private SurfaceTexture mSurfaceTexture;

    @Nullable
    private AbstractVideoPlayer mMediaPlayer;
    private Surface mSurface;

    public RenderTextureView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        mMeasureHelper = new MeasureHelper();
        setSurfaceTextureListener(this);
    }

    /**
     * 关联AbstractPlayer
     * @param player                        player
     */
    @Override
    public void attachToPlayer(@NonNull AbstractVideoPlayer player) {
        this.mMediaPlayer = player;
    }

    /**
     * 设置视频宽高
     * @param videoWidth                    宽
     * @param videoHeight                   高
     */
    @Override
    public void setVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth > 0 && videoHeight > 0) {
            mMeasureHelper.setVideoSize(videoWidth, videoHeight);
            requestLayout();
        }
    }

    /**
     * 设置视频旋转角度
     * @param degree                        角度值
     */
    @Override
    public void setVideoRotation(int degree) {
        mMeasureHelper.setVideoRotation(degree);
        setRotation(degree);
    }

    /**
     * 设置screen scale type
     * @param scaleType                     类型
     */
    @Override
    public void setScaleType(int scaleType) {
        mMeasureHelper.setScreenScale(scaleType);
        requestLayout();
    }

    /**
     * 获取真实的RenderView
     * @return                              view
     */
    @Override
    public View getView() {
        return this;
    }

    /**
     * 截图
     * @return                              bitmap
     */
    @Override
    public Bitmap doScreenShot() {
        return getBitmap();
    }

    /**
     * 释放资源
     */
    @Override
    public void release() {
        if (mSurface != null){
            mSurface.release();
        }
        if (mSurfaceTexture != null){
            mSurfaceTexture.release();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] measuredSize = mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measuredSize[0], measuredSize[1]);
    }

    /**
     * SurfaceTexture准备就绪
     * @param surfaceTexture            surface
     * @param width                     WIDTH
     * @param height                    HEIGHT
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        if (mSurfaceTexture != null) {
            setSurfaceTexture(mSurfaceTexture);
        } else {
            mSurfaceTexture = surfaceTexture;
            mSurface = new Surface(surfaceTexture);
            if (mMediaPlayer != null) {
                mMediaPlayer.setSurface(mSurface);
            }
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


    /**
     * SurfaceTexture缓冲大小变化
     * @param surface                   surface
     * @param width                     WIDTH
     * @param height                    HEIGHT
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    /**
     * SurfaceTexture即将被销毁
     * @param surface                   surface
     */
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    /**
     * SurfaceTexture通过updateImage更新
     * @param surface                   surface
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}