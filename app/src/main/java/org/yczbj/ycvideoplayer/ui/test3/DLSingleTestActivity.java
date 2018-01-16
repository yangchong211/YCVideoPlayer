package org.yczbj.ycvideoplayer.ui.test3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.pedaily.yc.ycdialoglib.toast.ToastUtil;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.ConstantVideo;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.util.LogUtils;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Description:
 * Update:2018/1/2
 * CreatedTime:2017/12/29
 * Author:yc
 */

public class DLSingleTestActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.start_btn_1)
    Button startBtn1;
    @Bind(R.id.pause_btn_1)
    Button pauseBtn1;
    @Bind(R.id.delete_btn_1)
    Button deleteBtn1;
    @Bind(R.id.filename_tv_1)
    TextView filenameTv1;
    @Bind(R.id.speed_tv_1)
    TextView speedTv1;
    @Bind(R.id.progressBar_1)
    ProgressBar progressBar1;
    @Bind(R.id.start_btn_2)
    Button startBtn2;
    @Bind(R.id.pause_btn_2)
    Button pauseBtn2;
    @Bind(R.id.delete_btn_2)
    Button deleteBtn2;
    @Bind(R.id.filename_tv_2)
    TextView filenameTv2;
    @Bind(R.id.speed_tv_2)
    TextView speedTv2;
    @Bind(R.id.progressBar_2)
    ProgressBar progressBar2;
    @Bind(R.id.start_btn_3)
    Button startBtn3;
    @Bind(R.id.pause_btn_3)
    Button pauseBtn3;
    @Bind(R.id.delete_btn_3)
    Button deleteBtn3;
    @Bind(R.id.speed_tv_3)
    TextView speedTv3;
    @Bind(R.id.progressBar_3)
    ProgressBar progressBar3;
    @Bind(R.id.start_btn_4)
    Button startBtn4;
    @Bind(R.id.pause_btn_4)
    Button pauseBtn4;
    @Bind(R.id.delete_btn_4)
    Button deleteBtn4;
    @Bind(R.id.detail_tv_4)
    TextView detailTv4;
    @Bind(R.id.speed_tv_4)
    TextView speedTv4;
    @Bind(R.id.progressBar_4)
    ProgressBar progressBar4;
    private String filePath1;
    private String filePath2;
    private String filePath3;
    private String filePath4;

    private int downloadId1;
    private int downloadId2;
    private int downloadId3;
    private int downloadId4;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().pause(downloadId1);
        FileDownloader.getImpl().pause(downloadId2);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_dl_single;
    }

    @Override
    public void initView() {
        setFilePath();
    }

    @Override
    public void initListener() {
        deleteBtn1.setOnClickListener(this);
        deleteBtn2.setOnClickListener(this);
        deleteBtn3.setOnClickListener(this);
        deleteBtn4.setOnClickListener(this);
        pauseBtn1.setOnClickListener(this);
        pauseBtn2.setOnClickListener(this);
        pauseBtn3.setOnClickListener(this);
        pauseBtn4.setOnClickListener(this);
        startBtn1.setOnClickListener(this);
        startBtn2.setOnClickListener(this);
        startBtn3.setOnClickListener(this);
        startBtn4.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * 设置文件路径
     */
    private void setFilePath() {
        filePath1 = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "_1_" + File.separator +"yc.apk";
        filePath2 = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "_2_";
        filePath3 = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "_3_";
        filePath4 = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "_4_";
        LogUtils.e(this.getClass().getName()+"--filePath1---"+filePath1);
        LogUtils.e(this.getClass().getName()+"--filePath2---"+filePath2);
        LogUtils.e(this.getClass().getName()+"--filePath3---"+filePath3);
        LogUtils.e(this.getClass().getName()+"--filePath4---"+filePath4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_btn_1:
                downloadId1 = createDownloadTask(1).start();
                break;
            case R.id.start_btn_2:

                break;
            case R.id.start_btn_3:

                break;
            case R.id.start_btn_4:

                break;
            case R.id.pause_btn_1:

                break;
            case R.id.pause_btn_2:

                break;
            case R.id.pause_btn_3:

                break;
            case R.id.pause_btn_4:

                break;
            case R.id.delete_btn_1:

                break;
            case R.id.delete_btn_2:

                break;
            case R.id.delete_btn_3:

                break;
            case R.id.delete_btn_4:

                break;
            default:
                break;
        }
    }

    private BaseDownloadTask createDownloadTask(int i) {
        final TaskTag taskTag ;
        //创建下载的链接地址
        String url = ConstantVideo.VideoPlayerList[0];
        //是否
        boolean isDir = false;
        //文件路径
        String path = null;

        switch (i){
            case 1:
                url = ConstantVideo.VideoPlayerList[0];
                path = filePath1;
                taskTag = new TaskTag(new WeakReference<>(this),filenameTv1, progressBar1, null, speedTv1, 1);
                break;
            case 2:
                url = ConstantVideo.VideoPlayerList[1];
                taskTag = new TaskTag(new WeakReference<>(this), filenameTv1, progressBar2, null, speedTv2, 2);
                path = filePath2;
                isDir = true;
                break;
            case 3:
                url = ConstantVideo.VideoPlayerList[2];
                taskTag = new TaskTag(new WeakReference<>(this),null, progressBar3, null, speedTv3, 3);
                path = filePath3;
                break;
            case 4:
                url = ConstantVideo.VideoPlayerList[2];
                taskTag = new TaskTag(new WeakReference<>(this),null, progressBar4, detailTv4, speedTv4, 4);
                path = filePath4;
                break;
            default:
                url = ConstantVideo.VideoPlayerList[2];
                taskTag = new TaskTag(new WeakReference<>(this),null, progressBar4, detailTv4, speedTv4, 4);
                path = filePath4;
                break;
        }
        return FileDownloader.getImpl().create(url)
                .setPath(path, isDir)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(taskTag)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        ((TaskTag) task.getTag()).updatePending(task);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        ((TaskTag) task.getTag()).updateProgress(soFarBytes, totalBytes,
                                task.getSpeed());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        ((TaskTag) task.getTag()).updateError(e, task.getSpeed());
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        ((TaskTag) task.getTag()).updateConnected(etag, task.getFilename());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        ((TaskTag) task.getTag()).updatePaused(task.getSpeed());
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        ((TaskTag) task.getTag()).updateCompleted(task);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        ((TaskTag) task.getTag()).updateWarn();
                    }
                });
    }

    public static class TaskTag {

        private TextView filenameTv;
        private ProgressBar pb;
        private TextView detailTv;
        private TextView speedTv;
        private int position;
        private WeakReference<DLSingleTestActivity> activityWeakReference;

        TaskTag(WeakReference<DLSingleTestActivity> activityWeakReference,TextView filenameTv
                , ProgressBar pb, TextView detailTv, TextView speedTv, int position) {
            this.activityWeakReference = activityWeakReference;
            this.filenameTv = filenameTv;
            this.pb = pb;
            this.detailTv = detailTv;
            this.speedTv = speedTv;
            this.position = position;
        }


        public void updatePending(BaseDownloadTask task) {
            if (filenameTv != null) {
                filenameTv.setText(task.getFilename());
            }
        }

        @SuppressLint("DefaultLocale")
        public void updateProgress(final int sofar, final int total, final int speed) {
            if (total == -1) {
                // chunked transfer encoding data
                pb.setIndeterminate(true);
            } else {
                pb.setMax(total);
                pb.setProgress(sofar);
            }
            updateSpeed(speed);
            if (detailTv != null) {
                detailTv.setText(String.format("sofar: %d total: %d", sofar, total));
            }
        }

        @SuppressLint("DefaultLocale")
        public void updateError(Throwable e, int speed) {
            toast(String.format("error %d %s", position, e));
            updateSpeed(speed);
            pb.setIndeterminate(false);
            e.printStackTrace();
        }

        public void updateConnected(String etag, String filename) {
            if (filenameTv != null) {
                filenameTv.setText(filename);
            }
        }

        @SuppressLint("DefaultLocale")
        public void updatePaused(int speed) {
            toast(String.format("paused %d", position));
            updateSpeed(speed);
            pb.setIndeterminate(false);
        }

        @SuppressLint("DefaultLocale")
        public void updateCompleted(BaseDownloadTask task) {
            toast(String.format("completed %d %s", position, task.getTargetFilePath()));

            if (detailTv != null) {
                detailTv.setText(String.format("sofar: %d total: %d",
                        task.getSmallFileSoFarBytes(), task.getSmallFileTotalBytes()));
            }
            updateSpeed(task.getSpeed());
            pb.setIndeterminate(false);
            pb.setMax(task.getSmallFileTotalBytes());
            pb.setProgress(task.getSmallFileSoFarBytes());
        }

        @SuppressLint("DefaultLocale")
        public void updateWarn() {
            toast(String.format("warn %d", position));
            pb.setIndeterminate(false);
        }


        @SuppressLint("DefaultLocale")
        private void updateSpeed(int speed) {
            if(speedTv!=null){
                speedTv.setText(String.format("%dKB/s", speed));
            }
        }

        private void toast(final String msg) {
            if (this.activityWeakReference != null && this.activityWeakReference.get() != null) {
                ToastUtil.showToast(activityWeakReference.get(),msg);
            }
        }
    }



}
