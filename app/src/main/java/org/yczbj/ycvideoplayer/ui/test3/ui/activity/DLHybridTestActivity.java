package org.yczbj.ycvideoplayer.ui.test3.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.ConstantVideo;
import org.yczbj.ycvideoplayer.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DLHybridTestActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.btn_4)
    Button btn4;
    @Bind(R.id.download_msg_tv)
    TextView downloadMsgTv;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.tip_msg_tv)
    TextView tipMsgTv;

    private final String TAG = "DLHybridTestActivity";
    private int totalCounts = 0;
    private int finalCounts = 0;
    private boolean needAuto2Bottom = true;



    @Override
    public int getContentView() {
        return R.layout.activity_test_dl_hybrid;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                deleteAll();
                break;
            case R.id.btn_2:
                SingleDownload();
                break;
            case R.id.btn_3:
                MultiSerialDl();
                break;
            case R.id.btn_4:
                MultiParallelDl();
                break;
            default:
                break;
        }
    }

    /**
     * 1.删除所有已经下载的文件
     */
    private void deleteAll() {
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        if (!file.exists()) {
            Log.w(TAG, String.format("check file files not exists %s", file.getAbsolutePath()));
            return;
        }

        if (!file.isDirectory()) {
            Log.w(TAG, String.format("check file files not directory %s", file.getAbsolutePath()));
            return;
        }

        File[] files = file.listFiles();

        if (files == null) {
            updateDisplay(getString(R.string.del_file_error_empty));
            return;
        }

        for (File file1 : files) {
            file1.delete();
            updateDisplay(getString(R.string.hybrid_test_deleted_file, file1.getName()));
        }
    }


    /**
     * 2.单任务下载测试
     */
    private void SingleDownload() {
        updateDisplay(getString(R.string.hybrid_test_start_single_task, ConstantVideo.VideoPlayerList[2]));
        totalCounts++;
        FileDownloader.getImpl()
                .create( ConstantVideo.VideoPlayerList[2])
                .setListener(createListener())
                .setTag(1)
                .start();
    }

    /**
     * 3.启动串行多任务下载
     */
    private void MultiSerialDl() {
        updateDisplay(getString(R.string.hybrid_test_start_multiple_tasks_serial, ConstantVideo.VideoPlayerList.length));

        // 以相同的listener作为target，将不同的下载任务绑定起来
        final List<BaseDownloadTask> taskList = new ArrayList<>();
        final FileDownloadListener serialTarget = createListener();
        int i = 0;
        for (String url : ConstantVideo.VideoPlayerList) {
            taskList.add(FileDownloader.getImpl().create(url)
                    .setTag(++i));
        }
        totalCounts += taskList.size();
        new FileDownloadQueueSet(serialTarget)
                .setCallbackProgressTimes(1)
                .downloadSequentially(taskList)
                .start();
    }

    /**
     * 4.启动并行多任务下载
     */
    private void MultiParallelDl() {
        updateDisplay(getString(R.string.hybrid_test_start_multiple_tasks_parallel, ConstantVideo.VideoPlayerList.length));
        // 以相同的listener作为target，将不同的下载任务绑定起来
        final FileDownloadListener parallelTarget = createListener();
        final List<BaseDownloadTask> taskList = new ArrayList<>();
        int i = 0;
        for (String url : ConstantVideo.VideoPlayerList) {
            taskList.add(FileDownloader.getImpl().create(url)
                    .setTag(++i));
        }
        totalCounts += taskList.size();

        new FileDownloadQueueSet(parallelTarget)
                .setCallbackProgressTimes(1)
                .downloadTogether(taskList)
                .start();
    }



    @SuppressLint("DefaultLocale")
    private void updateDisplay(final CharSequence msg) {
        if (downloadMsgTv.getLineCount() > 2500) {
            downloadMsgTv.setText("");
        }
        downloadMsgTv.append(String.format("\n %s", msg));
        tipMsgTv.setText(String.format("%d/%d", finalCounts, totalCounts));
        if (needAuto2Bottom) {
            scrollView.post(scroll2Bottom);
        }
    }


    private Runnable scroll2Bottom = new Runnable() {
        @Override
        public void run() {
            if (scrollView != null) {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        }
    };


    @SuppressLint("DefaultLocale")
    private FileDownloadListener createListener() {
        return new FileDownloadListener() {

            @Override
            protected boolean isInvalid() {
                return isFinishing();
            }

            @Override
            protected void pending(final BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                updateDisplay(String.format("[pending] id[%d] %d/%d", task.getId(), soFarBytes, totalBytes));
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                updateDisplay(String.format("[connected] id[%d] %s %B %d/%d", task.getId(), etag, isContinue, soFarBytes, totalBytes));
            }

            @Override
            protected void progress(final BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                updateDisplay(String.format("[progress] id[%d] %d/%d", task.getId(), soFarBytes, totalBytes));
            }

            @Override
            protected void blockComplete(final BaseDownloadTask task) {
                downloadMsgTv.post(new Runnable() {
                    @Override
                    public void run() {
                        updateDisplay(String.format("[blockComplete] id[%d]", task.getId()));
                    }
                });
            }

            @Override
            protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
                super.retry(task, ex, retryingTimes, soFarBytes);
                updateDisplay(String.format("[retry] id[%d] %s %d %d",
                        task.getId(), ex, retryingTimes, soFarBytes));
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                finalCounts++;
                updateDisplay(String.format("[completed] id[%d] oldFile[%B]",
                        task.getId(),
                        task.isReusedOldFile()));
                updateDisplay(String.format("---------------------------------- %d", (Integer) task.getTag()));
            }

            @Override
            protected void paused(final BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                finalCounts++;
                updateDisplay(String.format("[paused] id[%d] %d/%d", task.getId(), soFarBytes, totalBytes));
                updateDisplay(String.format("############################## %d", (Integer) task.getTag()));
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                finalCounts++;
                updateDisplay(Html.fromHtml(String.format("[error] id[%d] %s %s",
                        task.getId(),
                        e,
                        FileDownloadUtils.getStack(e.getStackTrace(), false))));

                updateDisplay(String.format("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! %d", (Integer) task.getTag()));
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                finalCounts++;
                updateDisplay(String.format("[warn] id[%d]", task.getId()));
                updateDisplay(String.format("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ %d", (Integer) task.getTag()));
            }
        };
    }

}
