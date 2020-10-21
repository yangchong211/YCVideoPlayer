package com.yc.ycvideoplayer.newPlayer.tiktok;

import android.content.Context;

import com.yc.video.surface.InterSurfaceView;
import com.yc.video.surface.SurfaceFactory;
import com.yc.video.surface.RenderTextureView;


public class TikTokRenderViewFactory extends SurfaceFactory {

    public static TikTokRenderViewFactory create() {
        return new TikTokRenderViewFactory();
    }

    @Override
    public InterSurfaceView createRenderView(Context context) {
        return new TikTokRenderView(new RenderTextureView(context));
    }
}
