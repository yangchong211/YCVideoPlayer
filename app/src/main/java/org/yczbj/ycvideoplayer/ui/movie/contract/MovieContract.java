package org.yczbj.ycvideoplayer.ui.movie.contract;


import org.yczbj.ycvideoplayer.base.mvp1.BasePresenter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseView;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.ui.special.model.SpecialBean;

import java.util.List;

/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */

public interface MovieContract {

    interface View extends BaseView {
        void setAdapterData(MovieBean movieBean);
    }

    interface Presenter extends BasePresenter {
        void getData();
    }


}
