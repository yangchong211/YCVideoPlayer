package org.yczbj.ycvideoplayer.tiktok;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.player.render.IRenderView;
import org.yczbj.ycvideoplayerlib.player.render.RenderViewFactory;
import org.yczbj.ycvideoplayerlib.player.render.TextureRenderView;


public class TikTokRenderViewFactory extends RenderViewFactory {

    public static TikTokRenderViewFactory create() {
        return new TikTokRenderViewFactory();
    }

    @Override
    public IRenderView createRenderView(Context context) {
        return new TikTokRenderView(new TextureRenderView(context));
    }
}
