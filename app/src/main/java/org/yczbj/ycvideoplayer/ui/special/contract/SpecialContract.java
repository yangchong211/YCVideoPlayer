package org.yczbj.ycvideoplayer.ui.special.contract;


import org.yczbj.ycvideoplayer.base.mvp1.BasePresenter;
import org.yczbj.ycvideoplayer.base.mvp1.BaseView;
import org.yczbj.ycvideoplayer.ui.special.model.SpecialBean;

import java.util.List;

/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */

public interface SpecialContract {

    interface View extends BaseView {
        void setAdapterView(List<SpecialBean> list);
    }

    interface Presenter extends BasePresenter {
        void getData();
    }


}
