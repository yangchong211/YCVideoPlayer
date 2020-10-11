package org.yczbj.ycvideoplayer.tiktok;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.surface.ISurfaceView;
import org.yczbj.ycvideoplayerlib.surface.SurfaceFactory;
import org.yczbj.ycvideoplayerlib.surface.RenderTextureView;


public class TikTokRenderViewFactory extends SurfaceFactory {

    public static TikTokRenderViewFactory create() {
        return new TikTokRenderViewFactory();
    }

    @Override
    public ISurfaceView createRenderView(Context context) {
        return new TikTokRenderView(new RenderTextureView(context));
    }
}
