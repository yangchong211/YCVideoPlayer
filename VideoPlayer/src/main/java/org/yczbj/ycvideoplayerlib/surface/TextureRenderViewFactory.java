package org.yczbj.ycvideoplayerlib.surface;

import android.content.Context;

public class TextureRenderViewFactory extends RenderViewFactory {

    public static TextureRenderViewFactory create() {
        return new TextureRenderViewFactory();
    }

    @Override
    public ISurfaceView createRenderView(Context context) {
        return new RenderTextureView(context);
    }
}
