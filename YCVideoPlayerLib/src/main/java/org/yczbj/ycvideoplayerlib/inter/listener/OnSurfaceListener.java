package org.yczbj.ycvideoplayerlib.inter.listener;


import android.graphics.SurfaceTexture;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : VideoTextureView监听
 *     revise:
 * </pre>
 */
public interface OnSurfaceListener {

    void onSurfaceAvailable(SurfaceTexture surface);

    void onSurfaceSizeChanged(SurfaceTexture surface, int width, int height);

    boolean onSurfaceDestroyed(SurfaceTexture surface);

    void onSurfaceUpdated(SurfaceTexture surface);

}
