package org.yczbj.ycvideoplayer.ui.movie.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.cn.ycbannerlib.first.adapter.StaticPagerAdapter;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.util.ImageUtil;

import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2016/11/12
 * 描    述：会议轮播图适配器
 * 修订历史：
 * ================================================
 */
public class MovieBannerAdapter extends StaticPagerAdapter {

    private Context ctx;
    private List<MovieBean.RetBean.ListBean.ChildListBean> list;

    public MovieBannerAdapter(Context ctx, List<MovieBean.RetBean.ListBean.ChildListBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //加载图片
        if(list!=null){
            ImageUtil.loadImgByPicasso(ctx,list.get(position).getPic(), R.drawable.image_default,imageView);
        }else {
            ImageUtil.loadImgByPicasso(ctx, R.drawable.image_default,imageView);
        }
        return imageView;
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }

}