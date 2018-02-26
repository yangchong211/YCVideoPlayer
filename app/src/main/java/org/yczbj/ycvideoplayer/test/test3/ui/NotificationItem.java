package org.yczbj.ycvideoplayer.test.test3.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.Utils;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.util.FileDownloadHelper;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ui.main.view.activity.MainActivity;
import org.yczbj.ycvideoplayer.test.test3.ui.activity.DLNotificationTestActivity;

/**
 * Created by yc on 2018/1/17.
 */

public class NotificationItem extends BaseNotificationItem {

    PendingIntent pendingIntent;
    NotificationCompat.Builder builder;

    public NotificationItem(int id, String title, String desc) {
        super(id, title, desc);
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeMainActivity(new ComponentName(Utils.getContext(), MainActivity.class));
        intents[1] = new Intent(Utils.getContext(), DLNotificationTestActivity.class);

        this.pendingIntent = PendingIntent.getActivities(Utils.getContext(), 0, intents,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.
                Builder(FileDownloadHelper.getAppContext());

        builder.setDefaults(Notification.DEFAULT_LIGHTS)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentTitle(getTitle())
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    @Override
    public void show(boolean statusChanged, int status, boolean isShowProgress) {

        String desc = getDesc();
        switch (status) {
            case FileDownloadStatus.pending:
                desc += " pending";
                break;
            case FileDownloadStatus.started:
                desc += " started";
                break;
            case FileDownloadStatus.progress:
                desc += " progress";
                break;
            case FileDownloadStatus.retry:
                desc += " retry";
                break;
            case FileDownloadStatus.error:
                desc += " error";
                break;
            case FileDownloadStatus.paused:
                desc += " paused";
                break;
            case FileDownloadStatus.completed:
                desc += " completed";
                break;
            case FileDownloadStatus.warn:
                desc += " warn";
                break;
            default:
                break;
        }

        builder.setContentTitle(getTitle()).setContentText(desc);
        if (statusChanged) {
            builder.setTicker(desc);
        }
        builder.setProgress(getTotal(), getSofar(), !isShowProgress);
        getManager().notify(getId(), builder.build());
    }


}
