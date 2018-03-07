package org.yczbj.ycvideoplayer.ui.movie.contract;


import org.yczbj.ycvideoplayer.base.mvp1.BasePresenter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseView;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieDetailBean;

/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */

public interface MovieDetailContract {

    interface View extends BaseView {
        void setAdapterData(MovieDetailBean movieDetailBean);
        void setError();
        void setEmptyView();
    }

    interface Presenter extends BasePresenter {
        void getData(String dataId);
    }


}
