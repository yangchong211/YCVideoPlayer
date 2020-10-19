package org.yczbj.ycvideoplayer.newPlayer.tiktok;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.surface.InterSurfaceView;
import org.yczbj.ycvideoplayerlib.surface.SurfaceFactory;
import org.yczbj.ycvideoplayerlib.surface.RenderTextureView;


public class TikTokRenderViewFactory extends SurfaceFactory {

    public static TikTokRenderViewFactory create() {
        return new TikTokRenderViewFactory();
    }

    @Override
    public InterSurfaceView createRenderView(Context context) {
        return new TikTokRenderView(new RenderTextureView(context));
    }
}
