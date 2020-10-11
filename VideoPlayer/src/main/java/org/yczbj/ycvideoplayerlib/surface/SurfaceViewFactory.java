package org.yczbj.ycvideoplayerlib.surface;

import android.content.Context;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 实现类
 *     revise:
 * </pre>
 */
public class SurfaceViewFactory extends SurfaceFactory {

    public static SurfaceViewFactory create() {
        return new SurfaceViewFactory();
    }

    @Override
    public ISurfaceView createRenderView(Context context) {
        return new RenderTextureView(context);
    }
}
