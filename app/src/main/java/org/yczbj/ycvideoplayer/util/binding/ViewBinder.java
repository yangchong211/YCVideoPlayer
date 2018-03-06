package org.yczbj.ycvideoplayer.util.binding;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * 仅限于练习项目，实际开发还是用ButtonKnife注解框架
 * @author yc
 */
public class ViewBinder {

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View source) {
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) != null) {
                        continue;
                    }
                    BindView bind = field.getAnnotation(BindView.class);
                    if (bind != null) {
                        int viewId = bind.value();
                        field.set(target, source.findViewById(viewId));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
