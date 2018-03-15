package org.yczbj.ycvideoplayer.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.ButterKnife;

/**
 * BaseDialog
 * Created by yc on 2015/12/28.
 */
public abstract class BaseDialog extends Dialog {


    public BaseDialog(Context context, int layoutId, int styleId) {
        super(context, styleId);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view ;
        if (inflater != null) {
            view = inflater.inflate(layoutId, null);
            this.setContentView(view);
            ButterKnife.bind(this);
        }
    }


}
