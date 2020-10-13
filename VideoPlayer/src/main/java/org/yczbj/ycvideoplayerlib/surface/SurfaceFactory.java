package org.yczbj.ycvideoplayerlib.surface;

import android.content.Context;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 扩展自己的渲染View
 *     revise: 可以使用TextureView，可参考{@link RenderTextureView}和{@link TextureViewFactory}的实现。
 * </pre>
 */
public abstract class SurfaceFactory {

    public abstract ISurfaceView createRenderView(Context context);

}
