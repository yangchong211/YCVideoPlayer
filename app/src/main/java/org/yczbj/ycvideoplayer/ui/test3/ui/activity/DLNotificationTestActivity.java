package org.yczbj.ycvideoplayer.ui.test3.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationHelper;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationListener;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test3.ui.NotificationItem;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.Bind;

/**
 * Created by yc on 2018/1/12.
 */

public class DLNotificationTestActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.show_notification_cb)
    CheckBox showNotificationCb;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btn_start_1)
    Button btnStart1;
    @Bind(R.id.btn_pause_1)
    Button btnPause1;
    @Bind(R.id.btn_del_1)
    Button btnDel1;
    @Bind(R.id.btn_start_2)
    Button btnStart2;
    @Bind(R.id.btn_pause_2)
    Button btnPause2;
    @Bind(R.id.btn_del_2)
    Button btnDel2;

    private final FileDownloadNotificationHelper<NotificationItem> notificationHelper = new FileDownloadNotificationHelper<>();
    private NotificationListener listener;

    private final String savePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "notification";
    private final String url = ConstantVideo.VideoPlayerList[1];
    private int downloadId = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadId != 0) {
            FileDownloader.getImpl().pause(downloadId);
        }
        notificationHelper.clear();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_test_dl_notification;
    }

    @Override
    public void initView() {
        listener = new NotificationListener(new WeakReference<>(this));
    }

    @Override
    public void initListener() {
        btnStart1.setOnClickListener(this);
        btnStart2.setOnClickListener(this);
        btnPause1.setOnClickListener(this);
        btnPause2.setOnClickListener(this);
        btnDel1.setOnClickListener(this);
        btnDel2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_1:
                if (downloadId == 0) {
                    downloadId = FileDownloader.getImpl().create(url)
                            .setPath(savePath)
                            .setListener(listener)
                            .start();
                }
                break;
            case R.id.btn_start_2:
                if (downloadId == 0) {
                    downloadId = FileDownloader.getImpl().create(url)
                            .setPath(savePath)
                            .setListener(new SecondNotificationListener(new FileDownloadNotificationHelper<NotificationItem>()))
                            .start();
                }
                break;
            case R.id.btn_pause_1:
                if (downloadId != 0) {
                    FileDownloader.getImpl().pause(downloadId);
                    downloadId = 0;
                }
                break;
            case R.id.btn_pause_2:
                if (downloadId != 0) {
                    FileDownloader.getImpl().pause(downloadId);
                    downloadId = 0;
                }
                break;
            case R.id.btn_del_1:
                final File file = new File(savePath);
                if (file.exists()) {
                    file.delete();
                }
                downloadId = 0;
                break;
            case R.id.btn_del_2:
                final File file2 = new File(savePath);
                if (file2.exists()) {
                    file2.delete();
                }
                downloadId = 0;
                break;
            default:
                break;
        }
    }

    private static class NotificationListener extends FileDownloadNotificationListener {

        private WeakReference<DLNotificationTestActivity> wActivity;

        public NotificationListener(WeakReference<DLNotificationTestActivity> wActivity) {
            super(wActivity.get().notificationHelper);
            this.wActivity = wActivity;
        }

        @Override
        protected BaseNotificationItem create(BaseDownloadTask task) {
            return new NotificationItem(task.getId(), "sample demo title", "sample demo desc");
        }

        @Override
        public void addNotificationItem(BaseDownloadTask task) {
            super.addNotificationItem(task);
            if (wActivity.get() != null) {
                wActivity.get().showNotificationCb.setEnabled(false);
            }
        }

        @Override
        public void destroyNotification(BaseDownloadTask task) {
            super.destroyNotification(task);
            if (wActivity.get() != null) {
                wActivity.get().showNotificationCb.setEnabled(true);
                wActivity.get().downloadId = 0;
            }
        }

        @Override
        protected boolean interceptCancel(BaseDownloadTask task,
                                          BaseNotificationItem n) {
            // in this demo, I don't want to cancel the notification, just show for the test
            // so return true
            return true;
        }

        @Override
        protected boolean disableNotification(BaseDownloadTask task) {
            if (wActivity.get() != null) {
                return !wActivity.get().showNotificationCb.isChecked();
            }

            return super.disableNotification(task);
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            if (wActivity.get() != null) {
                wActivity.get().progressBar.setIndeterminate(true);
            }
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            if (wActivity.get() != null) {
                wActivity.get().progressBar.setIndeterminate(false);
                wActivity.get().progressBar.setMax(totalBytes);
                wActivity.get().progressBar.setProgress(soFarBytes);
            }
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            if (wActivity.get() != null) {
                wActivity.get().progressBar.setIndeterminate(false);
                wActivity.get().progressBar.setProgress(task.getSmallFileTotalBytes());
            }
        }
    }


    private class SecondNotificationListener extends FileDownloadNotificationListener {

        private static final String TAG = "NotificationListener";

        public SecondNotificationListener(FileDownloadNotificationHelper helper) {
            super(helper);
        }

        @Override
        protected BaseNotificationItem create(BaseDownloadTask task) {
            return new NotificationItem(task.getId(), "min set demo title", " min set demo desc");
        }

        @Override
        public void destroyNotification(BaseDownloadTask task) {
            super.destroyNotification(task);
            Toast.makeText(DLNotificationTestActivity.this, "destroyNotification() called with: status " + task.getStatus(), Toast.LENGTH_LONG).show();
            downloadId = 0;
        }
    }



}
