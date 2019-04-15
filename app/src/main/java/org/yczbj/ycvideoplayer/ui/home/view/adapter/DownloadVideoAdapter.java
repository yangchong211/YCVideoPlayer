package org.yczbj.ycvideoplayer.ui.home.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.ns.yc.ycprogresslib.CircleProgressbar;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.download.TaskViewHolderImp;
import org.yczbj.ycvideoplayer.download.TasksManager;
import org.yczbj.ycvideoplayer.ui.home.model.DialogListBean;
import org.yczbj.ycvideoplayer.util.LogUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DownloadVideoAdapter extends RecyclerView.Adapter<DownloadVideoAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<DialogListBean> mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private ViewHolder viewHolder;
    //下载状态
    private final String STATE_START = "start";
    private final String STATE_STOP = "stop";
    private final String STATE_DETAIL = "detail";

    public DownloadVideoAdapter(Context mContext, List<DialogListBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        TasksManager.getImpl().onCreate(new WeakReference<>(this));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewHolder = new ViewHolder(inflater.inflate(R.layout.item_dialog_list_view, parent, false));
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //final TasksManagerModel model = TasksManager.getImpl().get(position);
        DialogListBean model = mList.get(position);
        final String path = TasksManager.getImpl().createPath(model.getVideo());
        int id = TasksManager.getImpl().getId(model.getVideo());
        holder.update(id, position);
        TasksManager.getImpl().updateViewHolder(holder.id, holder);

        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvTime.setText("时长98:00：12");
        holder.tvVideoSize.setText("100MB");
        holder.ivDownload.setBackgroundResource(R.drawable.icon_cache_download);

        //设置类型
        holder.circlePb.setProgressType(CircleProgressbar.ProgressType.COUNT);
        //设置圆形的填充颜色
        holder.circlePb.setInCircleColor(mContext.getResources().getColor(R.color.colorTransparent));
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineColor(mContext.getResources().getColor(R.color.gray3));
        //设置进度监听
        holder.circlePb.setCountdownProgressListener(1, progressListener);
        //设置外部轮廓的颜色
        holder.circlePb.setOutLineWidth(1);
        //设置进度条线的宽度
        holder.circlePb.setProgressLineWidth(3);
        //设置进度
        holder.circlePb.setProgress(0);

        //状态: 加载中...
        LogUtils.e(DownloadVideoAdapter.class.getName()+" ----- "+holder.toString());
        holder.llDownload.setTag(holder);
        holder.tvDownloadState.setTag(STATE_START);
        holder.llDownload.setOnClickListener(listener);

        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });


        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(model.getId(), path);
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else if (!new File(path).exists() && !new File(FileDownloadUtils.getTempPath(path)).exists()) {
                // not exist file
                holder.updateNotDownloaded(status, 0, 0);
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else {
                // not start
                holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            }
        } else {
            //状态: 加载中...
            LogUtils.e(DownloadVideoAdapter.class.getName()+" ----- 状态: 加载中...");
        }
    }

    @Override
    public int getItemCount() {
        return mList==null ? 0 :mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements TaskViewHolderImp {

        private int position;
        public int id;

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_download)
        LinearLayout llDownload;
        @BindView(R.id.iv_download)
        ImageView ivDownload;
        @BindView(R.id.circle_pb)
        CircleProgressbar circlePb;
        @BindView(R.id.tv_video_size)
        TextView tvVideoSize;
        @BindView(R.id.tv_download_state)
        TextView tvDownloadState;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void update(int id, int position) {
            this.id = id;
            this.position = position;
        }

        @Override
        public void updateDownloaded() {
            LogUtils.e(DownloadVideoAdapter.class.getName()+" ----- updateDownloaded: ");
            //当下载完成后，隐藏圆环控件，显示删除图标
            circlePb.setVisibility(View.GONE);
            circlePb.setProgress(1);
            ivDownload.setBackgroundResource(R.drawable.icon_cache_delete);
            tvDownloadState.setTag(STATE_DETAIL);
            tvDownloadState.setText("下载完成");

            /*TasksManager.getImpl().deleteTasksManagerModel(videolistBean.getVideoUrl());
            if (!TasksManager.getImpl().isExistDownloadFile(videolistBean.getVideoUrl())) {
                TasksManager.getImpl().addDownloaded(videolistBean);
            }*/
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void updateNotDownloaded(int status, long sofar, long total) {
            LogUtils.e(DownloadVideoAdapter.class.getName()+" ----- updateNotDownloaded: ");
            if (sofar > 0 && total > 0) {
                final float percent = sofar / (float) total;
                circlePb.setProgress((int) (percent * 100));
            } else {
                circlePb.setProgress(0);
            }
            switch (status) {
                case FileDownloadStatus.error:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvDownloadState.setTag(STATE_START);
                    tvDownloadState.setText("错误");
                    break;
                case FileDownloadStatus.paused:
                    tvVideoSize.setText(String.format("%dMB",total/1024/1024));
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvDownloadState.setTag(STATE_START);
                    tvDownloadState.setText("暂停");
                    break;
                default:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvDownloadState.setTag(STATE_START);
                    break;
            }
        }

        @Override
        public void updateDownloading(int status, long sofar, long total) {
            LogUtils.e(DownloadVideoAdapter.class.getName()+" ----- updateDownloading: ");
            final float percent = sofar / (float) total;
            circlePb.setProgress((int) (percent * 100));
            switch (status) {
                case FileDownloadStatus.pending:
                    tvDownloadState.setText("排队中");
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvDownloadState.setTag(STATE_START);
                    break;
                case FileDownloadStatus.started:
                    tvDownloadState.setText("开始下载");
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvDownloadState.setTag(STATE_START);
                    break;
                case FileDownloadStatus.connected:
                    tvDownloadState.setText("链接中");
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    tvDownloadState.setTag(STATE_START);
                    break;
                case FileDownloadStatus.progress:
                    tvDownloadState.setText("下载中");
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_play);
                    tvDownloadState.setTag(STATE_STOP);
                    break;
                default:
                    ivDownload.setBackgroundResource(R.drawable.icon_cache_play);
                    tvDownloadState.setTag(STATE_STOP);
                    break;
            }
        }
    }


    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                return;
            }
            Object tag = v.getTag();
            LogUtils.e(DownloadVideoAdapter.class.getName()+" ----- " +tag.toString());
            ViewHolder holder = (ViewHolder) v.getTag();
            String state = (String) holder.tvDownloadState.getTag();
            DialogListBean model = mList.get(holder.position);
            switch (state){
                //下载
                case STATE_START:
                    String path = TasksManager.getImpl().createPath(model.getVideo());
                    final BaseDownloadTask task = FileDownloader.getImpl().create(model.getVideo())
                            .setPath(path)
                            .setCallbackProgressTimes(500)
                            .setListener(taskDownloadListener);
                    TasksManager.getImpl().addTaskForViewHolder(task);
                    TasksManager.getImpl().updateViewHolder(holder.id, holder);
                    task.start();
                    viewHolder.tvDownloadState.setText("开始下载");
                    LogUtils.e(DownloadVideoAdapter.class.getName()+" --开始下载--- "+ holder.position
                            + "-----" +model.getVideo());
                    break;
                //暂停
                case STATE_STOP:
                    //ToastUtil.showToast(mContext,"暂停下载");
                    FileDownloader.getImpl().pause(holder.id);
                    holder.ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    holder.tvDownloadState.setTag(STATE_START);
                    viewHolder.tvDownloadState.setText("暂停下载");
                    LogUtils.e(DownloadVideoAdapter.class.getName()+" --暂停下载--- "+ holder.position
                            + "-----" +model.getVideo());
                    break;
                //删除
                case STATE_DETAIL:
                    //ToastUtil.showToast(mContext,"删除文件");
                    // to delete
                    // File file = new File(TasksManager.getImpl().get(holder.position).getPath());
                    // 删除文件file.delete();
                    File file = new File(TasksManager.getImpl().createPath(model.getVideo()));
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                    holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
                    holder.ivDownload.setBackgroundResource(R.drawable.icon_cache_download);
                    holder.tvDownloadState.setTag(STATE_START);
                    holder.circlePb.setVisibility(View.VISIBLE);
                    viewHolder.tvDownloadState.setText("已经删除");
                    LogUtils.e(DownloadVideoAdapter.class.getName()+" --已经删除--- "+ holder.position
                            + "-----" +model.getVideo());
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 这个是用来监听下载的情况[没问题]
     * 1.下载队列中
     * 2.开始下载
     * 3.连接中
     * 4.下载中
     * 5.下载错误
     * 6.暂停
     * 7.完成
     */
    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

        private ViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final ViewHolder tag = (ViewHolder) task.getTag();
            if (tag.id != task.getId()) {
                return null;
            }
            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
            tag.tvDownloadState.setText("队列中");
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.tvDownloadState.setText("状态: 开始下载");
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
            tag.tvDownloadState.setText("状态: 连接中");
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
            tag.tvDownloadState.setText("状态: 下载中");
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
            tag.tvDownloadState.setText("状态: 错误");
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            tag.tvDownloadState.setText("状态: 暂停");
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            final ViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }
            tag.updateDownloaded();
            tag.tvDownloadState.setText("状态: 完成");
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }
    };


    private CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                viewHolder.circlePb.setText("0");
            }
        }
    };

    public void postNotifyDataChanged() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int id);
    }


}
