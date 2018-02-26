package org.yczbj.ycvideoplayer.util;

import android.support.annotation.NonNull;

import org.yczbj.ycvideoplayer.model.LoadingBean;
import org.yczbj.ycvideoplayer.model.LoadingEndBean;
import org.yczbj.ycvideoplayer.model.binder.LoadingEndViewBinder;
import org.yczbj.ycvideoplayer.model.binder.LoadingViewBinder;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleDataBean;
import org.yczbj.ycvideoplayer.ui.video.model.binder.NewsArticleVideoViewBinder;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by yc on 2018/2/26.
 */

public class Register {

    public static void registerVideoArticleItem(@NonNull MultiTypeAdapter adapter) {
        adapter.register(MultiNewsArticleDataBean.class, new NewsArticleVideoViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }

}
